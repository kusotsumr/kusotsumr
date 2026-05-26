package musicbandlab.client.consoleui;

import java.util.HashSet;

public class ExecuteScriptHelper {
    private final int maxNestingLevel = 100;
    private final HashSet<String> scriptsSet = new HashSet<>();
    private int recursionLevel = 0;

    /**
     * 'Входит' в скрипт
     * <p>
     * Если файл с названием fileName исполняется рекурсивно в рамках этого же файла,
     * или вложенность в executeScript превышает предельную,
     * то возвращаем false.
     * <p>
     * Иначе возвращаем true, повышаем текущую вложенность,
     * и сохраняем fileName для дальнейших проверок рекурсии
     */
    public boolean TryEnter(String fileName) {
        if(scriptsSet.contains(fileName) || recursionLevel >= maxNestingLevel) {
            return false;
        }

        scriptsSet.add(fileName);
        recursionLevel++;

        return true;
    }

    /**
     * 'Выходит' из скрипта
     * <p>
     * Понижаем текущую вложенность, и забываем fileName
     * т.е. не учитываем его в дальнейших проверках
     */
    public void Exit(String fileName) {
        scriptsSet.remove(fileName);
        recursionLevel--;
    }
}