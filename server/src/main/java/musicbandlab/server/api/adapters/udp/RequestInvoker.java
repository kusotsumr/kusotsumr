package musicbandlab.server.api.adapters.udp;

import musicbandlab.common.contracts.Request;
import musicbandlab.server.api.HandlerRegistry;

public class RequestInvoker {
    private final HandlerRegistry registry;

    public RequestInvoker(HandlerRegistry registry) {
        this.registry = registry;
    }

    public Object handle(Object request) {
        musicbandlab.server.core.application.usecases.RequestHandler<?, ?> handler =
                registry.get(request.getClass());

        if (handler == null) {
            throw new RuntimeException("No handler");
        }

        Object result = invoke(handler, request);
        return result;
    }

    private Object invoke(musicbandlab.server.core.application.usecases.RequestHandler handler, Object request) {
        return handler.handle((Request) request);
    }
}