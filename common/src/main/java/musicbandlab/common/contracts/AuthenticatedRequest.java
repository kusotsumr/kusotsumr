package musicbandlab.common.contracts;

import java.io.Serializable;
import java.util.Objects;

/**
 * Обёртка над любым Request, добавляющая логин и пароль отправителя.
 * Клиент заворачивает в неё каждую исходящую команду ОДИН раз, в ServerGateway.
 * Сервер разворачивает её ОДИН раз, в RequestReader, до того как отдать
 * внутренний request в HandlerRegistry/RequestInvoker — сами хендлеры
 * и HandlerRegistry ничего не знают об авторизации и не меняются.
 */
public final class AuthenticatedRequest<TResult extends Serializable> implements Serializable {
    private final String login;
    private final String password;
    private final Request<TResult> request;

    public AuthenticatedRequest(String login, String password, Request<TResult> request) {
        this.login = Objects.requireNonNull(login, "login");
        this.password = Objects.requireNonNull(password, "password");
        this.request = Objects.requireNonNull(request, "request");
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public Request<TResult> getRequest() {
        return request;
    }
}
