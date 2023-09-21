package org.parsingbot.parser.service;

import org.parsingbot.entity.Event;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

/**
 * Сервис для взаимодействия с парсером в удобном для бота формате
 */
public interface ParserService {

    /**
     * @param event обертка над update, user и command
     * @return список сообщений для пользователя (каждое сообщение содержит по одной вакансии)
     */
    List<SendMessage> getVacanciesMessageList(Event event);

    /**
     * @param event обертка над update, user и command
     * @return сообщений для пользователя (все вакансии в одном сообщении)
     */
    SendMessage getVacanciesSingleMessage(Event event);
}