package uz.sardorbroo.jinx.core.command.impl;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.exec.*;
import uz.sardorbroo.jinx.core.command.CommandExecutor;
import uz.sardorbroo.jinx.enumeration.Command;
import uz.sardorbroo.jinx.pojo.CommandResult;
import uz.sardorbroo.jinx.pojo.Flag;
import uz.sardorbroo.jinx.pojo.Result;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

@Slf4j
@Builder
public class ApacheCommandExecutor implements CommandExecutor {

    private Executor executor;

    private InputStream in;
    private OutputStream success;
    private OutputStream error;

    public ApacheCommandExecutor(Executor executor,
                                 InputStream in,
                                 OutputStream success,
                                 OutputStream error) {
        this.executor = executor;
        this.in = in;
        this.success = success;
        this.error = error;
    }

    public ApacheCommandExecutor(InputStream in,
                                 OutputStream success,
                                 OutputStream error) {
        this.in = in;
        this.success = success;
        this.error = error;

        this.executor = new DefaultExecutor();
        ExecuteStreamHandler handler = new PumpStreamHandler(success, error, in);
        executor.setStreamHandler(handler);
    }

    @Override
    public Result execute(Command command) {
        log.debug("Executing command. Command: {}", command);

        if (Objects.isNull(command)) {
            log.warn("Invalid argument has passed! Command must not be null!");
            throw new IllegalArgumentException("Invalid argument has passed! Command must not be null!");
        }

        CommandLine cl = parse(command);

        try {
            executor.execute(cl);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CommandResult result = new CommandResult();
        result.setSuccess(true);
        result.setCommand(command.getName());

        log.info("Command has executed successfully. Command: {}", command);
        return result;
    }

    private CommandLine parse(Command command) {
        return CommandLine.parse("cmd.exe /c" + command.getName());
    }

    @Override
    public Result execute(Command command, Flag flag) {
        return null;
    }

    public CommandLine parse(Command command, Flag flag) {
        return CommandLine.parse(String.format("%s %s", command.getName(), flag.getName()));
    }
}
