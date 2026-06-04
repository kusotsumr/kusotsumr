package musicbandlab.client.consoleui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Сервис-локатор для предоставления зависимостей приложения.
 * Инициализирует репозиторий и все обработчики команд и запросов.
 * Поддерживает загрузку коллекции из файла при запуске.
 */
public class ServiceLocator {
    private final MusicBandApplication musicBandApplication;
    private final ExecuteScriptHelper executeScriptHelper;
    private final ServerGateway serverGateway;

    public ServiceLocator(Config config, MusicBandApplication musicBandApplication) {
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        this.musicBandApplication = musicBandApplication;
        this.serverGateway = new ServerGateway(config);
        this.executeScriptHelper = new ExecuteScriptHelper();
    }

    public ExecuteScriptHelper getExecuteScriptHelper() {
        return executeScriptHelper;
    }

    public MusicBandApplication getMusicBandApplication() {
        return musicBandApplication;
    }

    public ServerGateway getServerGateway() {
        return serverGateway;
    }
}