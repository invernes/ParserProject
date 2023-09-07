package org.parsingbot.configuration;

import org.parsingbot.entity.CommandEnum;
import org.parsingbot.service.UserService;
import org.parsingbot.service.VacancyService;
import org.parsingbot.service.commands.CommandHandler;
import org.parsingbot.service.commands.CommandHandlerDispatcher;
import org.parsingbot.service.commands.CommandParser;
import org.parsingbot.service.commands.impl.CommandHandlerDispatcherImpl;
import org.parsingbot.service.commands.impl.SubscribeCommandHandler;
import org.parsingbot.service.commands.impl.UnsubscribeCommandHandler;
import org.parsingbot.service.commands.impl.hh.HhCommandHandler;
import org.parsingbot.service.commands.impl.hh.HhCommandParser;
import org.parsingbot.service.handlers.ResponseHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CommandHandlerConfiguration {

    @Bean
    public CommandParser hhCommandParser() {
        return new HhCommandParser();
    }

    @Bean
    public CommandHandler hhCommandHandler(ResponseHandler responseHandler,
                                           VacancyService vacancyService,
                                           UserService userService,
                                           CommandParser hhCommandParser) {
        return new HhCommandHandler(
                responseHandler,
                vacancyService,
                userService,
                hhCommandParser);
    }

    @Bean
    public CommandHandler subscribeCommandHandler(UserService userService,
                                                  ResponseHandler responseHandler) {
        return new SubscribeCommandHandler(
                userService,
                responseHandler);
    }

    @Bean
    public CommandHandler unsubscribeCommandHandler(UserService userService,
                                                    ResponseHandler responseHandler) {
        return new UnsubscribeCommandHandler(
                userService,
                responseHandler);
    }

    @Bean
    @Qualifier("commandHandlerMap")
    public Map<String, CommandHandler> commandHandlerMap(CommandHandler hhCommandHandler,
                                                         CommandHandler subscribeCommandHandler,
                                                         CommandHandler unsubscribeCommandHandler) {
        Map<String, CommandHandler> commandHandlerMap = new HashMap<>();
        commandHandlerMap.put(CommandEnum.HH_COMMAND.getCommandDto().getPrefix(), hhCommandHandler);
        commandHandlerMap.put(CommandEnum.SUBSCRIBE_COMMAND.getCommandDto().getPrefix(), subscribeCommandHandler);
        commandHandlerMap.put(CommandEnum.UNSUBSCRIBE_COMMAND.getCommandDto().getPrefix(), unsubscribeCommandHandler);
        return commandHandlerMap;
    }

    @Bean
    public CommandHandlerDispatcher commandHandlerDispatcher(
            @Qualifier("commandHandlerMap") Map<String, CommandHandler> commandHandlerMap) {
        return new CommandHandlerDispatcherImpl(commandHandlerMap);
    }
}
