package musicbandlab.consoleui;

/**
 * Точка входа в программу.
 * Запускает приложение MusicBandApplication, передавая путь к файлу.
 */
public class Main {
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Ошибка: программа принимает только путь к файлу");
            return;
        }

        MusicBandApplication application = new MusicBandApplication(args[0]);
        application.run();
    }
}