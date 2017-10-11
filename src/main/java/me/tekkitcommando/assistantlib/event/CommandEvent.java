package me.tekkitcommando.assistantlib.event;

import me.tekkitcommando.assistantlib.Assistant;

import java.util.Arrays;

public class CommandEvent extends Event {

    private Assistant assistant;

    public CommandEvent(Assistant assistant, String commandTranscript) {
        this.assistant = assistant;
        handleEvent(commandTranscript);
    }

    private void handleEvent(String commandTranscript) {
        String transcript[] = commandTranscript.split(" ");
        String command = transcript[0];
        String args[] = Arrays.copyOfRange(transcript, 1, transcript.length);

        assistant.getCommandHandler().handleCommand(command, args);
    }
}
