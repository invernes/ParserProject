package org.parsingbot.core.service.commands.misc;

import lombok.RequiredArgsConstructor;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.User;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.core.service.commands.CommandHandler;
import org.parsingbot.core.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.io.Serializable;
import java.util.List;

@RequiredArgsConstructor
public class SubscribeCommandHandler implements CommandHandler {

    private static final String USER_ALREADY_SUBSCRIBED = "User is already subscribed";
    private static final String SUCCESSFUL_SUBSCRIBED = "You have been successfully subscribed";

    private final UserService userService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        long chatId = event.getChatId();
        User user = event.getUser();

        if (user.getIsSubscribed()) {
            return List.of(BotUtils.createMessage(chatId, USER_ALREADY_SUBSCRIBED));
        }

        user.setIsSubscribed(true);
        userService.save(user);
        return List.of(BotUtils.createMessage(chatId, SUCCESSFUL_SUBSCRIBED));
    }
}