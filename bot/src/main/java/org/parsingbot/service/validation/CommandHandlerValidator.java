package org.parsingbot.service.validation;

import org.parsingbot.entity.User;
import org.parsingbot.service.commands.CommandHandler;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

/**
 * Класс, реализующий проверку возможности вызова команды пользователем
 */
public interface CommandHandlerValidator {

    /**
     * @param commandHandler обработчик команды
     * @param user           объект пользователя
     * @return сообщение пользователю, если он не может пользоваться командой (null в противном случае)
     */
    SendMessage getCommandInvocationError(CommandHandler commandHandler, User user);

}