package com.evheniy.botconstruct;

import com.evheniy.botconstruct.commands.CommandHandler;
import com.evheniy.botconstruct.commands.HelpCommandHandler;
import com.evheniy.botconstruct.commands.TestCommandHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CommandHandlerConfiguration {

    @Bean
    public List<CommandHandler> commandHandlers() {
        return Arrays.asList(
                new HelpCommandHandler(),
                new TestCommandHandler()
        );
    }
}