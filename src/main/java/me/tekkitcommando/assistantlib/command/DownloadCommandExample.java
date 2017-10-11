package me.tekkitcommando.assistantlib.command;

public class DownloadCommandExample extends Command {

    @Override
    public void onCommand(String command, String[] args) {
        if (command.equalsIgnoreCase("download")) {
            if (args.length < 2) {
                System.out.println("Structure of command is download <fileName> from <serviceName>");
            } else {
                // Check if service exists and download specified file
            }
        }
    }
}
