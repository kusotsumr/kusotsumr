package musicbandlab.server.api.adapters.udp;

import musicbandlab.common.contracts.AuthenticatedRequest;
import musicbandlab.common.contracts.Request;
import musicbandlab.common.contracts.UnitResponse;
import musicbandlab.common.contracts.commands.register.RegisterCommand;
import musicbandlab.common.exceptions.RemoteServerException;
import musicbandlab.server.core.application.usecases.CurrentUserContext;
import musicbandlab.server.core.ports.UserRepository;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class RequestReader {
    private final RequestInvoker requestInvoker;
    private final RequestSender requestSender;
    private final UserRepository userRepository;

    public RequestReader(RequestInvoker requestInvoker, RequestSender requestSender, UserRepository userRepository) {
        this.requestInvoker = requestInvoker;
        this.requestSender = requestSender;
        this.userRepository = userRepository;
    }

    public void read(DatagramChannel channel, byte[] data, InetSocketAddress client) {
        Object incoming;
        try {
            try (ByteArrayInputStream bais = new ByteArrayInputStream(data);
                 ObjectInputStream ois = new ObjectInputStream(bais)) {
                incoming = ois.readObject();
            }
        } catch (Exception e) {
            sendError(channel, client, e);
            return;
        }

        if (!(incoming instanceof AuthenticatedRequest<?> authenticatedRequest)) {
            sendError(channel, client, new IllegalArgumentException("Запрос должен быть авторизован"));
            return;
        }

        //в задании так надо было
        new Thread(() -> process(channel, client, authenticatedRequest)).start();
    }

    private void process(DatagramChannel channel, InetSocketAddress client, AuthenticatedRequest<?> authenticatedRequest) {
        String login = authenticatedRequest.getLogin();
        String password = authenticatedRequest.getPassword();
        Request<?> innerRequest = authenticatedRequest.getRequest();

        Object result;
        try {
            if (innerRequest instanceof RegisterCommand) {
                boolean registered = userRepository.register(login, password);
                if (!registered) {
                    throw new IllegalArgumentException("Логин уже занят");
                }
                result = UnitResponse.INSTANCE;
            } else {
                if (!userRepository.verify(login, password)) {
                    throw new SecurityException("Неверный логин или пароль");
                }

                CurrentUserContext.set(login);
                try {
                    result = requestInvoker.handle(innerRequest);
                } finally {
                    CurrentUserContext.clear();
                }
            }
        } catch (Exception e) {
            result = new RemoteServerException(e.getMessage());
        }

        if (result == null) return;

        Object finalResult = result;
        // Отправка ответа — тоже в отдельном java.lang.Thread, по требованию задания
        new Thread(() -> {
            try {
                requestSender.sendResult(channel, client, finalResult);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendError(DatagramChannel channel, InetSocketAddress client, Exception e) {
        try {
            requestSender.sendResult(channel, client, new RemoteServerException(e.getMessage()));
        } catch (Exception sendException) {
            sendException.printStackTrace();
        }
    }
}