package musicbandlab.server.api;

import musicbandlab.server.core.application.usecases.RequestHandler;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HandlerRegistry {

    private final Map<Class<?>, RequestHandler<?, ?>> handlerByClass = new HashMap<>();

    public HandlerRegistry(List<RequestHandler<?, ?>> handlers) {
        for (RequestHandler<?, ?> handler : handlers) {
            register(handler);
        }
    }

    private void register(RequestHandler<?, ?> handler) {

        for (Type type : handler.getClass().getGenericInterfaces()) {
            if (!(type instanceof ParameterizedType p)) continue;

            Type arg = p.getActualTypeArguments()[0];

            Class<?> requestType;

            if (arg instanceof Class<?> c) {
                requestType = c;
            } else {
                throw new RuntimeException("Error");
            }

            handlerByClass.put(requestType, handler);
            return;
        }

        throw new RuntimeException("Error");
    }

    public RequestHandler<?, ?> get(Class<?> requestType) {
        return handlerByClass.get(requestType);
    }
}