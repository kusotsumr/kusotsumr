package musicbandlab.client.consoleui;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import musicbandlab.client.consoleui.annotations.CommandAnnotation;
import musicbandlab.client.consoleui.commands.AbstractConsoleCommand;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Класс для выполнения команд, введенных пользователем.
 * По ключевому слову находит класс команды с помощью аннотации CommandAnnotation,
 * создает экземпляр и вызывает метод execute.
 */
public class CommandExecutor {
    private final ServiceLocator serviceLocator;

    public CommandExecutor(ServiceLocator serviceLocator) {
        this.serviceLocator = serviceLocator;
    }

    public void executeCommand(Scanner scanner, PrintStream systemMessagesStream, String line) throws Exception {
        if(line == null || line.isEmpty())
            throw new IllegalArgumentException("Line should be not empty");

        String[] parts = line.split(" ");
        if(parts.length == 0)
            throw new IllegalArgumentException("Line should has at least one part");
        String currentKeyword = parts[0];

        ScanResult scan = new ClassGraph().enableAnnotationInfo().scan();
        ArrayList<ClassInfo> classInfos = scan.getClassesWithAnnotation(CommandAnnotation.class.getName());
        AbstractConsoleCommand instance;

        for (ClassInfo classInfo : classInfos) {
            AnnotationInfo ann = classInfo.getAnnotationInfo(CommandAnnotation.class.getName());

            if (ann != null) {
                Object helpValue = ann.getParameterValues().getValue("keyword");
                if (currentKeyword.equals(helpValue)) {
                        Class<?> clazz = classInfo.loadClass();

                        instance = (AbstractConsoleCommand) clazz.getDeclaredConstructor(ServiceLocator.class, Scanner.class, PrintStream.class, String[].class)
                                .newInstance(serviceLocator, scanner, systemMessagesStream, parts);

                        instance.execute();

                        scan.close();
                        return;
                }
            }
        }

        System.out.println("Команда не найдена!");

        scan.close();
    }
}