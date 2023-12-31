package org.parsingbot.core.service.receiver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.commons.entity.Command;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.core.service.commands.CommandHandler;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class CommandHandlerDispatcher {

    private final Map<String, CommandHandler> startCommandHandlerMap;
    private final Map<State, CommandHandler> commandHandlerMap;

    public CommandHandler getCommandHandler(Event event) {
        User user = event.getUser();
        Command command = event.getCommand();

        if (startCommandHandlerMap.containsKey(command.getPrefix())) {
            return startCommandHandlerMap.get(command.getPrefix());
        }
        return commandHandlerMap.getOrDefault(State.valueOf(user.getState()), null);
    }
}