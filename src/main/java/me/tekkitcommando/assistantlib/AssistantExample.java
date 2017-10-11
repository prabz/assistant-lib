package me.tekkitcommando.assistantlib;

import me.tekkitcommando.assistantlib.command.CommandHandler;
import me.tekkitcommando.assistantlib.command.DownloadCommandExample;
import me.tekkitcommando.assistantlib.service.ServiceHandler;

// This will be updated as the library becomes more complete
public class AssistantExample {

    public static void main(String[] args) {
        // Creates the assistant with a name to listen for (if enabled), a CommandHandler, and a ServiceHandler
        // The command handler calls the appropriate command executor, which then can call a service from the
        // service handler or do something completely custom.
        Assistant assistant = new Assistant("Assistant", new CommandHandler(), new ServiceHandler());
        // Adds the command created with the command api to the list of executors the command handler can call
        assistant.registerCommand(new DownloadCommandExample());
    }
}
