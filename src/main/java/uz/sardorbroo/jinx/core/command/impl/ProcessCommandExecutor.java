package uz.sardorbroo.jinx.core.command.impl;

import lombok.extern.slf4j.Slf4j;
import uz.sardorbroo.jinx.core.command.CommandExecutor;
import uz.sardorbroo.jinx.enumeration.Command;
import uz.sardorbroo.jinx.pojo.CommandResult;
import uz.sardorbroo.jinx.pojo.Flag;
import uz.sardorbroo.jinx.pojo.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Slf4j
public class ProcessCommandExecutor implements CommandExecutor {

    @Override
    public Result execute(Command command) {

        ProcessBuilder builder = new ProcessBuilder("docker");
        builder.redirectErrorStream(true);

        Process process;
        try {
            process = builder.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        var is = process.getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String result = reader.lines().collect(Collectors.joining());

            CommandResult r = new CommandResult();
            r.setCommand(command.getName());
            r.setResult(result);
            r.setSuccess(true);

            System.out.println(result);
            return r;
        } catch (Exception e) {

        }

        return null;
    }

    @Override
    public Result execute(Command command, Flag flag) {
        return null;
    }
}
