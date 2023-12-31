package org.parsingbot.core.service.commands.misc;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.service.Authorisation;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.core.service.commands.CommandHandler;
import org.parsingbot.core.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class DropStateCommandHandler implements CommandHandler {

    private static final String STATE_CHANGED = "State changed to %s";

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        State newState = State.NONE;
        try {
            newState = State.valueOf(event.getCommand().getMessageWithoutPrefix().toUpperCase());
        } catch (Exception e) {
            log.warn("State {} doesn't exist. User.state was set to State.NONE", event.getCommand().getMessageWithoutPrefix());
        }
        User user = event.getUser();
        user.setState(newState.toString());
        userService.save(user);
        log.info("State for user with chatId {} was set to {}", event.getChatId(), newState);
        return List.of(BotUtils.createMessage(event.getChatId(), String.format(STATE_CHANGED, newState)));
    }

    @Override
    public State getRequiredState() {
        return State.ANY;
    }

    @Override
    public Authorisation getRequiredAuthorisation() {
        return Authorisation.ADMIN;
    }
}