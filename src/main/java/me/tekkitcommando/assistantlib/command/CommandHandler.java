package me.tekkitcommando.assistantlib.command;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {

    private Map<String, Command> commands = new HashMap<>();

    public void addCommand(String commandLabel, Command command) {
        if (command != null) {
            commands.put(commandLabel, command);
        } else {
            System.out.println("Command is null!");
        }
    }

    public void handleCommand(String commandLabel, String args[]) {
        commands.get(commandLabel).executeCommand(commandLabel, args);
    }
}
