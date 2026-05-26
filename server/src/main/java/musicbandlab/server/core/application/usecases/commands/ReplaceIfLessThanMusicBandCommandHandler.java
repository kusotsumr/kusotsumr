package musicbandlab.server.core.application.usecases.commands;

import musicbandlab.common.contracts.commands.replaceiflessthanmusicband.ReplaceIfLessThanMusicBandCommand;
import musicbandlab.common.contracts.commands.replaceiflessthanmusicband.ReplaceIfLessThanMusicBandCommandResponse;
import musicbandlab.server.core.application.usecases.RequestHandler;
import musicbandlab.server.core.ports.MusicBandRepository;

import java.util.Objects;

/**
 * Реализация обработчика команды замены элемента (если новый меньше старого).
 */
public class ReplaceIfLessThanMusicBandCommandHandler
        implements RequestHandler<ReplaceIfLessThanMusicBandCommand, ReplaceIfLessThanMusicBandCommandResponse> {
    private final MusicBandRepository repository;

    public ReplaceIfLessThanMusicBandCommandHandler(MusicBandRepository repository) {
        this.repository = repository;
    }

    @Override
    public ReplaceIfLessThanMusicBandCommandResponse handle(ReplaceIfLessThanMusicBandCommand request) {
        Objects.requireNonNull(request, "request");
        boolean isSuccess = repository.replaceIfLessThan(request.getKey(), request.getMusicBand());

        return new ReplaceIfLessThanMusicBandCommandResponse(isSuccess);
    }
}