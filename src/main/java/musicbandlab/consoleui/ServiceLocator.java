package musicbandlab.consoleui;

import musicbandlab.core.application.usecases.commands.clearmusicbands.ClearMusicBandsCommandHandler;
import musicbandlab.core.application.usecases.commands.clearmusicbands.ClearMusicBandsCommandHandlerImpl;
import musicbandlab.core.application.usecases.commands.insertmusicband.InsertMusicBandCommandHandler;
import musicbandlab.core.application.usecases.commands.insertmusicband.InsertMusicBandCommandHandlerImpl;
import musicbandlab.core.application.usecases.commands.replaceifgreaterthanmusicband.ReplaceIfGreaterThanMusicBandCommandHandler;
import musicbandlab.core.application.usecases.commands.replaceifgreaterthanmusicband.ReplaceIfGreaterThanMusicBandCommandHandlerImpl;
import musicbandlab.core.application.usecases.commands.replaceiflessthanmusicband.ReplaceIfLessThanMusicBandCommandHandler;
import musicbandlab.core.application.usecases.commands.replaceiflessthanmusicband.ReplaceIfLessThanMusicBandCommandHandlerImpl;
import musicbandlab.core.application.usecases.commands.removemusicband.RemoveMusicBandCommandHandler;
import musicbandlab.core.application.usecases.commands.removemusicband.RemoveMusicBandCommandHandlerImpl;
import musicbandlab.core.application.usecases.commands.removewherelessmusicbands.RemoveWhereLessMusicBandsCommandHandler;
import musicbandlab.core.application.usecases.commands.removewherelessmusicbands.RemoveWhereLessMusicBandsCommandHandlerImpl;
import musicbandlab.core.application.usecases.commands.updatemusicband.UpdateMusicBandCommandHandler;
import musicbandlab.core.application.usecases.commands.updatemusicband.UpdateMusicBandCommandHandlerImpl;
import musicbandlab.core.application.usecases.queries.getallmusicbands.GetAllMusicBandsQueryHandler;
import musicbandlab.core.application.usecases.queries.getallmusicbands.GetAllMusicBandsQueryHandlerImpl;
import musicbandlab.core.application.usecases.queries.getcountbynumberofparticipants.GetCountByNumberOfParticipantsQueryHandler;
import musicbandlab.core.application.usecases.queries.getcountbynumberofparticipants.GetCountByNumberOfParticipantsQueryHandlerImpl;
import musicbandlab.core.application.usecases.queries.gethashcode.GetHashCodeQueryHandler;
import musicbandlab.core.application.usecases.queries.gethashcode.GetHashCodeQueryHandlerImpl;
import musicbandlab.core.application.usecases.queries.getmusicbandsinfo.GetMusicBandsInfoQueryHandler;
import musicbandlab.core.application.usecases.queries.getmusicbandsinfo.GetMusicBandsInfoQueryHandlerImpl;
import musicbandlab.core.application.usecases.queries.getmusicbandslabelsdescending.GetMusicBandsLabelsDescendingQueryHandler;
import musicbandlab.core.application.usecases.queries.getmusicbandslabelsdescending.GetMusicBandsLabelsDescendingQueryHandlerImpl;
import musicbandlab.core.application.usecases.queries.getmusicbandswherelabellessthan.GetMusicBandsWhereLabelLessThanQueryHandler;
import musicbandlab.core.application.usecases.queries.getmusicbandswherelabellessthan.GetMusicBandsWhereLabelLessThanQueryHandlerImpl;
import musicbandlab.core.application.usecases.queries.getsumnumberofparticipants.GetSumNumberOfParticipantsQueryHandler;
import musicbandlab.core.application.usecases.queries.getsumnumberofparticipants.GetSumNumberOfParticipantsQueryHandlerImpl;
import musicbandlab.core.ports.MusicBandRepository;
import musicbandlab.infrastructure.hashtable.MusicBandRepositoryImpl;

import java.io.FileReader;
import java.io.IOException;

/**
 * Сервис-локатор для предоставления зависимостей приложения.
 * Инициализирует репозиторий и все обработчики команд и запросов.
 * Поддерживает загрузку коллекции из файла при запуске.
 */
public class ServiceLocator {
    private final ClearMusicBandsCommandHandler clearMusicBandsCommandHandler;
    private final InsertMusicBandCommandHandler insertMusicBandCommandHandler;
    private final ReplaceIfGreaterThanMusicBandCommandHandler replaceIfGreaterThanMusicBandCommandHandler;
    private final ReplaceIfLessThanMusicBandCommandHandler replaceIfLessThanMusicBandCommandHandler;
    private final RemoveMusicBandCommandHandler removeMusicBandCommandHandler;
    private final RemoveWhereLessMusicBandsCommandHandler removeWhereLessMusicBandsCommandHandler;
    private final UpdateMusicBandCommandHandler updateMusicBandCommandHandler;
    private final GetAllMusicBandsQueryHandler getAllMusicBandsQueryHandler;
    private final GetHashCodeQueryHandler getHashCodeQueryHandler;
    private final GetSumNumberOfParticipantsQueryHandler getSumNumberOfParticipantsQueryHandler;
    private final GetCountByNumberOfParticipantsQueryHandler getCountByNumberOfParticipantsQueryHandler;
    private final GetMusicBandsInfoQueryHandler getMusicBandsInfoQueryHandler;
    private final GetMusicBandsLabelsDescendingQueryHandler getMusicBandsLabelsDescendingQueryHandler;
    private final GetMusicBandsWhereLabelLessThanQueryHandler getMusicBandsWhereLabelLessThanQueryHandler;
    private final MusicBandRepository musicBandRepository;
    private final String fileName;
    private final MusicBandApplication musicBandApplication;

    public ServiceLocator(String fileName, MusicBandApplication musicBandApplication) {
        this.musicBandApplication = musicBandApplication;
        MusicBandRepository repository = new MusicBandRepositoryImpl();;
        if(fileName != null) {
            try {
                String json = readAllText(fileName);
                repository =  MusicBandRepositoryImpl.deserializeFromJson(json);;
            } catch (IOException e) {
                System.out.println("Error while reading file. New collection was created");;
            }
        }
        this.fileName = fileName;

        clearMusicBandsCommandHandler = new ClearMusicBandsCommandHandlerImpl(repository);
        insertMusicBandCommandHandler = new InsertMusicBandCommandHandlerImpl(repository);
        replaceIfGreaterThanMusicBandCommandHandler = new ReplaceIfGreaterThanMusicBandCommandHandlerImpl(repository);
        replaceIfLessThanMusicBandCommandHandler = new ReplaceIfLessThanMusicBandCommandHandlerImpl(repository);
        removeMusicBandCommandHandler = new RemoveMusicBandCommandHandlerImpl(repository);
        removeWhereLessMusicBandsCommandHandler = new RemoveWhereLessMusicBandsCommandHandlerImpl(repository);
        updateMusicBandCommandHandler = new UpdateMusicBandCommandHandlerImpl(repository);
        getAllMusicBandsQueryHandler = new GetAllMusicBandsQueryHandlerImpl(repository);
        getHashCodeQueryHandler = new GetHashCodeQueryHandlerImpl(repository);
        getSumNumberOfParticipantsQueryHandler = new GetSumNumberOfParticipantsQueryHandlerImpl(repository);
        getCountByNumberOfParticipantsQueryHandler = new GetCountByNumberOfParticipantsQueryHandlerImpl(repository);
        getMusicBandsInfoQueryHandler = new GetMusicBandsInfoQueryHandlerImpl(repository);
        getMusicBandsLabelsDescendingQueryHandler = new GetMusicBandsLabelsDescendingQueryHandlerImpl(repository);
        getMusicBandsWhereLabelLessThanQueryHandler = new GetMusicBandsWhereLabelLessThanQueryHandlerImpl(repository);
        musicBandRepository = repository;
    }

    public ClearMusicBandsCommandHandler getClearMusicBandsCommandHandler() {
        return clearMusicBandsCommandHandler;
    }

    public InsertMusicBandCommandHandler getInsertMusicBandCommandHandler() {
        return insertMusicBandCommandHandler;
    }

    public ReplaceIfGreaterThanMusicBandCommandHandler getReplaceIfGreaterThanMusicBandCommandHandler() {
        return replaceIfGreaterThanMusicBandCommandHandler;
    }

    public ReplaceIfLessThanMusicBandCommandHandler getReplaceIfLessThanMusicBandCommandHandler() {
        return replaceIfLessThanMusicBandCommandHandler;
    }

    public RemoveMusicBandCommandHandler getRemoveMusicBandCommandHandler() {
        return removeMusicBandCommandHandler;
    }

    public RemoveWhereLessMusicBandsCommandHandler getRemoveWhereLessMusicBandsCommandHandler() {
        return removeWhereLessMusicBandsCommandHandler;
    }

    public UpdateMusicBandCommandHandler getUpdateMusicBandCommandHandler() {
        return updateMusicBandCommandHandler;
    }

    public GetAllMusicBandsQueryHandler getGetAllMusicBandsQueryHandler() {
        return getAllMusicBandsQueryHandler;
    }

    public GetHashCodeQueryHandler getGetHashCodeQueryHandler() {
        return getHashCodeQueryHandler;
    }

    public GetSumNumberOfParticipantsQueryHandler getGetSumNumberOfParticipantsHandler() {
        return getSumNumberOfParticipantsQueryHandler;
    }

    public GetCountByNumberOfParticipantsQueryHandler getGetCountByNumberOfParticipantsQueryHandler() {
        return getCountByNumberOfParticipantsQueryHandler;
    }

    public GetMusicBandsInfoQueryHandler getGetMusicBandsInfoQueryHandler() {
        return getMusicBandsInfoQueryHandler;
    }

    public GetMusicBandsLabelsDescendingQueryHandler getGetMusicBandsLabelsDescendingQueryHandler() {
        return getMusicBandsLabelsDescendingQueryHandler;
    }

    public GetMusicBandsWhereLabelLessThanQueryHandler getGetMusicBandsWhereLabelLessThanQueryHandler() {
        return getMusicBandsWhereLabelLessThanQueryHandler;
    }

    public MusicBandRepository getMusicBandRepository() {
        return musicBandRepository;
    }

    private String readAllText(String fileName) throws IOException {
        FileReader fileReader = new FileReader(fileName);
        StringBuilder stringBuilder = new StringBuilder();

        int symbol;
        while ((symbol = fileReader.read()) != -1) {
            stringBuilder.append((char) symbol);
        }

        fileReader.close();
        return stringBuilder.toString();
    }

    public String getFileName() {
        return fileName;
    }

    public MusicBandApplication getMusicBandApplication() {
        return musicBandApplication;
    }
}