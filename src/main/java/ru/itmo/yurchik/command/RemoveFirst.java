package ru.itmo.yurchik.command;

import ru.itmo.yurchik.collection.DragonCollection;
import ru.itmo.yurchik.command.base.Command;
import ru.itmo.yurchik.command.base.Environment;
import ru.itmo.yurchik.command.exception.CommandException;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

public class RemoveFirst extends Command {
    private DragonCollection dragonCollection;
    public RemoveFirst(DragonCollection dragonCollection) {
        super("remove_first");
        this.dragonCollection = dragonCollection;
    }

    @Override
    public void execute(Environment env, InputStream stdin, PrintStream stdout) throws CommandException {
        if (!dragonCollection.getDragons().isEmpty()) {
            dragonCollection.getDragons().removeFirst();
            System.out.println("Successfully remove first dragon");
        } else {
            throw new CommandException("No dragons to remove");
        }
    }

    @Override
    public String getHelp() {
        return "Remove first element of collection";
    }
}
