package me.tekkitcommando.assistantlib;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import me.tekkitcommando.assistantlib.command.Command;
import me.tekkitcommando.assistantlib.command.CommandHandler;
import me.tekkitcommando.assistantlib.service.ServiceHandler;

@RequiredArgsConstructor
public class Assistant {

    @Getter
    private final String name;
    @Getter
    private final CommandHandler commandHandler;
    @Getter
    private final ServiceHandler serviceHandler;

    public void registerCommand(Command command) {
        commandHandler.addCommand(command);
    }
}
