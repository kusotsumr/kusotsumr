package musicbandlab.common.contracts.commands.register;

import musicbandlab.common.contracts.Request;
import musicbandlab.common.contracts.UnitResponse;

/**
 * Команда регистрации нового пользователя.
 * Собственных полей нет намеренно: логин и пароль для регистрации —
 * это те же login/password, что лежат в обёртке AuthenticatedRequest,
 * в которую эта команда всё равно заворачивается перед отправкой.
 */
public class RegisterCommand implements Request<UnitResponse> {
}