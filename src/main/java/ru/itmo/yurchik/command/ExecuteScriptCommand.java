package ru.itmo.yurchik.command;

import ru.itmo.yurchik.command.base.Command;
import ru.itmo.yurchik.command.base.Environment;
import ru.itmo.yurchik.command.exception.CommandException;
import ru.itmo.yurchik.csvReaderWriter.*;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * Класс для выполнения команда из выбранного файла
 */
public class ExecuteScriptCommand extends Command {
    /**
     * Конструктор команды
     */
    public ExecuteScriptCommand() {
        super("execute_script");
    }

    /**
     * Выполнить команду (использовать скрипт)
     *
     * @param env
     * @param stdin
     * @param stdout
     * @param comArgs
     * @throws CommandException
     */
    @Override
    public void execute(Environment env, InputStream stdin, PrintStream stdout, String[] comArgs) throws CommandException {
        String fileName = comArgs[0];

        CsvReader reader = new CsvReader();
        List<String> commands = reader.readCommandsFromFile(fileName);

        if (commands.isEmpty()) {
            stdout.println("Ошибка: файл не содержит команд или не может быть прочитан.");
            return;
        }

        HashMap<String, Command> mapOfCommands = env.getStringCommandHashMap();

        for (String line : commands) {
            line = line.trim();
            if (mapOfCommands.containsKey(line)) {
                Command command = mapOfCommands.get(line);

                try {
                    if(command.getName() == "execute_script"){
                        System.err.println("Execute_script не может быть исполнена в файле -_-");
                        continue;
                    }
                    command.execute(env, System.in, System.out, comArgs);
                } catch (CommandException e) {
                    System.err.println("Ошибка при выполнении команды: " + e.getMessage());
                }
            } else {
                System.err.println("Неизвестная команда: " + line + "\nВведите <help> для списка команд.");
            }
        }
    }

    /**
     * Метод для получения описания команды
     * @return
     */
    @Override
    public String getHelp() {
        return "consider and execute the script from the specified file. The script contains commands in the same form in which they are introduced by the user in interactive mode";
    }
}
