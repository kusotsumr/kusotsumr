package musicbandlab.consoleui.commands;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import musicbandlab.consoleui.ServiceLocator;
import musicbandlab.consoleui.annotations.CommandAnnotation;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


/**
 * Команда для вывода справки по доступным командам.
 * Сканирует все классы с аннотацией CommandAnnotation и выводит текст справки (help) для каждой команды.
 */
@CommandAnnotation(
    help = "help: вывести справку по доступным командам",
    keyword = "help"
)
public class HelpCommand extends AbstractConsoleCommand {
    public HelpCommand(ServiceLocator serviceLocator, Scanner scanner, PrintStream systemMessagesStream, String[] parts) {
        super(serviceLocator, scanner, systemMessagesStream, parts, 1);
    }

    @Override
    public void execute() {
        ScanResult scan = new ClassGraph().enableAnnotationInfo().scan();

        ArrayList<ClassInfo> classes = scan.getClassesWithAnnotation(CommandAnnotation.class.getName());

        System.out.println("Доступные команды: ");
        for (ClassInfo classInfo : classes) {
            AnnotationInfo ann = classInfo.getAnnotationInfo(CommandAnnotation.class.getName());
            if (ann != null) {
                Object helpValue = ann.getParameterValues().getValue("help");
                if (helpValue != null) {
                    System.out.println((String) helpValue);
                }
            }
        }

        scan.close();
    }
}