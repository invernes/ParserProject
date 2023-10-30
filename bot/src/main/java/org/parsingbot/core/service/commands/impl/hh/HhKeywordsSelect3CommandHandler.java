package org.parsingbot.core.service.commands.impl.hh;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.parsingbot.commons.entity.Event;
import org.parsingbot.commons.entity.State;
import org.parsingbot.commons.entity.User;
import org.parsingbot.core.parser.service.ParserService;
import org.parsingbot.commons.service.UserService;
import org.parsingbot.core.service.commands.CommandHandler;
import org.parsingbot.core.util.BotUtils;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class HhKeywordsSelect3CommandHandler implements CommandHandler {

    private static final String FINAL_MESSAGE = "Результаты поиска по запросу вакансия = '%s'," +
            "количество вакансий = '%d', ключевые слова = {%s}";

    private final UserService userService;
    private final ParserService parserService;

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handleCommand(Event event) {
        User user = event.getUser();

        String keywords = event.getCommand().getFullMessage();
        if (StringUtils.isNotBlank(keywords)) {
            user.setKeywords(keywords);
            userService.save(user);
        }

        List<SendMessage> messagesToUser = new ArrayList<>();
        messagesToUser.add(BotUtils.createMessage(
                event.getChatId(),
                String.format(FINAL_MESSAGE, user.getVacancyName(), user.getNumberOfVacancies(), user.getKeywords()))
        );

        List<SendMessage> vacancies = parserService.getVacanciesMessageList(event);
        messagesToUser.addAll(vacancies);

        userService.setDefaultStateByUser(user);
        return List.copyOf(messagesToUser);
    }

    @Override
    public State getRequiredState() {
        return State.HH_KEYWORDS_SELECT_3;
    }
}
