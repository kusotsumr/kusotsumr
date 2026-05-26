package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.commands.replaceifgreaterthanmusicband.ReplaceIfGreaterThanMusicBandCommand;
import musicbandlab.common.contracts.commands.replaceifgreaterthanmusicband.ReplaceIfGreaterThanMusicBandCommandResponse;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды замены элемента (если новый больше старого).
 */
public class ReplaceIfGreaterThanMusicBandCommandHandler
        implements RequestHandler<ReplaceIfGreaterThanMusicBandCommand, ReplaceIfGreaterThanMusicBandCommandResponse> {
    private final MusicBandRepository repository;

    public ReplaceIfGreaterThanMusicBandCommandHandler(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReplaceIfGreaterThanMusicBandCommandResponse handle(ReplaceIfGreaterThanMusicBandCommand request) {
        Objects.requireNonNull(request, "request");
        boolean isSuccess = repository.replaceIfGreaterThan(request.getKey(), request.getMusicBand());

        return new ReplaceIfGreaterThanMusicBandCommandResponse(isSuccess);
    }
}