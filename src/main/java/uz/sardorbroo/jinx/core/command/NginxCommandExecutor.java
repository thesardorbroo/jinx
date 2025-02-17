package uz.sardorbroo.jinx.core.command;

import uz.sardorbroo.jinx.core.command.enumeration.NginxSignal;
import uz.sardorbroo.jinx.pojo.Result;

public interface NginxCommandExecutor {

    Result help();

    Result getVersion();

    Result getVersionAndMore();

    Result test();

    Result testAndMore();

    Result signal(NginxSignal signal);
}
