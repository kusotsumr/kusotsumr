package musicbandlab.server.core.application.usecases;

import musicbandlab.common.contracts.Request;
import java.io.Serializable;

public interface RequestHandler<TQuery extends Request<TResult>, TResult extends Serializable> {
    TResult handle(TQuery query);
}