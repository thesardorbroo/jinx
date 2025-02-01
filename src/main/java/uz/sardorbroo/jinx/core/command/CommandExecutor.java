package uz.sardorbroo.jinx.core.command;

import uz.sardorbroo.jinx.enumeration.Command;
import uz.sardorbroo.jinx.pojo.Flag;
import uz.sardorbroo.jinx.pojo.Result;

import java.util.Map;

public interface CommandExecutor {

    boolean doesSupport(Command command);

    Result execute(Command command, String value);

    Result execute(Command command, String value, Map<Flag, String> flagValues);

}
