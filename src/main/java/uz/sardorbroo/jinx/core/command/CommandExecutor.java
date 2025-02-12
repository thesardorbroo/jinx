package uz.sardorbroo.jinx.core.command;

import uz.sardorbroo.jinx.enumeration.Command;
import uz.sardorbroo.jinx.pojo.Flag;
import uz.sardorbroo.jinx.pojo.Result;

public interface CommandExecutor {

    Result execute(Command command);

    Result execute(Command command, Flag flag);

}
