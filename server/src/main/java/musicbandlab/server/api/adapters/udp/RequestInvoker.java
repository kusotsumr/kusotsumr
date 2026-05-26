package musicbandlab.server.api.adapters.udp;

import musicbandlab.common.contracts.Request;
import musicbandlab.server.api.HandlerRegistry;

import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class RequestInvoker {
    private final HandlerRegistry registry;

    public RequestInvoker(HandlerRegistry registry) {
        this.registry = registry;
    }

    public Object handle(Class<?> requestType, Object requestObj) {
        musicbandlab.server.core.application.usecases.RequestHandler<?, ?> handler =
                registry.get(requestType);

        if (handler == null) {
            throw new RuntimeException("No handler");
        }

        Object result = invoke(handler, requestObj);
        return result;
    }

    private Object invoke(musicbandlab.server.core.application.usecases.RequestHandler handler, Object request) {
        return handler.handle((Request) request);
    }
}