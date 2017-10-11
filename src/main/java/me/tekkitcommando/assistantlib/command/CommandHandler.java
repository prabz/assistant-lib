package me.tekkitcommando.assistantlib.command;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

    private List<Command> commands = new ArrayList<>();

    public void addCommand(Command command) {
        if (command != null) {
            commands.add(command);
        } else {
            System.out.println("Command is null!");
        }
    }
}
