package uz.sardorbroo.jinx.enumeration;

import lombok.Getter;
import lombok.experimental.Accessors;
import uz.sardorbroo.jinx.pojo.Flag;

import java.util.Set;

import static uz.sardorbroo.jinx.constans.NginxConstants.*;

@Getter
@Accessors(chain = true)
public enum Command {

    NGINX("nginx", Set.of(HELP, VERSION, VERSION_AND_MORE_INFO, TEST, TEST_AND_MORE, SIGNAL)),
    ;
    private final String name;
    private final Set<Flag> flags;

    Command(String name, Set<Flag> flags) {
        this.name = name;
        this.flags = flags;
    }
}
