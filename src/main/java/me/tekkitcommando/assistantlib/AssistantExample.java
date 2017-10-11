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
        assistant.registerCommand("download", new DownloadCommandExample());

        // The audio recorder will stop listening after 5 seconds and see if the interpreted command is registered
        Thread stopListening = new Thread(() -> {
            try {
                Thread.sleep(5 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assistant.stopListening();
        });

        // Starts the thread
        stopListening.start();
        // Starts listening to mic audio for a command
        assistant.startListening();
        // Handling the closing of the speech client is up to the lib user
        try {
            assistant.getClient().close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
