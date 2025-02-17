package uz.sardorbroo.jinx.core.command.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.command.CommandExecutor;
import uz.sardorbroo.jinx.pojo.CommandResult;
import uz.sardorbroo.jinx.pojo.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProcessCommandExecutor implements CommandExecutor {

    @Override
    public Result execute(String... commands) {
        log.debug("Executing command. Commands: {}", commands);

        if (Objects.isNull(commands) || commands.length == 0) {
            log.warn("Invalid argument has passed! Command must not be null!");
            throw new IllegalArgumentException("Invalid argument has passed! Command must not be null!");
        }

        ProcessBuilder builder = new ProcessBuilder(commands);
        builder.redirectErrorStream(true);

        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            log.error("Error while executing command!. Commands: {} | Exception message: {}", commands, e.getMessage());
            log.debug("Stack trace: ", e);
            throw new RuntimeException(e);
        }

        var is = process.getInputStream();
        CommandResult r = null;
        String commandAsSingle = commandAsSingle(commands);
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String result = reader.lines().collect(Collectors.joining());

            r = new CommandResult();
            r.setCommand(commandAsSingle);
            r.setResult(result);
            r.setSuccess(true);

        } catch (Exception e) {
            log.error("Error while executing command! Exception: {}", e.getMessage());
            log.debug("Stack trace: ", e);

            r = new CommandResult();
            r.setCommand(commandAsSingle);
            r.setResult(e.getMessage());
            r.setSuccess(Boolean.FALSE);

        }

        log.debug("Command has executed. Result: {}", r);
        return r;
    }

    private String commandAsSingle(String... commands) {
        return String.join(" ", commands);
    }

}
