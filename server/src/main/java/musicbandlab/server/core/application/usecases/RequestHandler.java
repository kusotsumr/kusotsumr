package musicbandlab.server.core.application.usecases;

import musicbandlab.common.contracts.Request;

public interface RequestHandler<TQuery extends Request<TResult>, TResult> {
    TResult handle(TQuery query);
}