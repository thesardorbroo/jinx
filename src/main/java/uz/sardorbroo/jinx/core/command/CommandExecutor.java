package uz.sardorbroo.jinx.core.command;

import uz.sardorbroo.jinx.pojo.Result;

public interface CommandExecutor {

    /**
     * Give commands and args
     *
     * <p>For example:</p>
     * <li>docker -v</li>
     * <li>git -h</li>
     *
     * @param commands
     * @return {@link Result}
     */
    Result execute(String... commands);

}
