package ru.itmo.yurchik;

import ru.itmo.yurchik.command.ExitCommand;
import ru.itmo.yurchik.command.HelpCommand;
import ru.itmo.yurchik.command.base.Command;
import ru.itmo.yurchik.command.HelloCommand;
import ru.itmo.yurchik.command.TestCommand;
import ru.itmo.yurchik.command.base.Environment;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //new Dragon(1234124, "xyesos", new Coordinates(), 1, 10L, Color.BLUE, DragonType.AIR, DragonCharacter.CHAOTIC, new DragonCave());

        Scanner in = new Scanner(System.in);
        System.out.print("Введите команду: ");

        HashMap<String, Command> map = new HashMap<>();

        TestCommand testCommand = new TestCommand();
        map.put(testCommand.getName(), testCommand);
        //"test" -> testCommand
        HelloCommand helloCommand = new HelloCommand();
        map.put(helloCommand.getName(), helloCommand);
        //"hello" -> helloCommand
        ExitCommand exitCommand = new ExitCommand();
        map.put(exitCommand.getName(), exitCommand);
        HelpCommand helpCommand = new HelpCommand();
        map.put(helpCommand.getName(), helpCommand);

        Environment environment = new Environment(map);

        //для контроля потоков
        InputStream inputStream = System.in;
        PrintStream printStream = System.out;

        while (in.hasNext()) {
            String line = in.nextLine();
            if (map.keySet().contains(line)) {
                Command command = map.get(line);
                command.execute(environment, System.in, System.out);
            } else {
                System.err.println("Unknown command: " + line + "\n Введите <help> для списка команд");
            }
        }
    }
}