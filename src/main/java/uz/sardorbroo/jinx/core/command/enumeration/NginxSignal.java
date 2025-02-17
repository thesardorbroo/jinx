package uz.sardorbroo.jinx.core.command.enumeration;

import lombok.Getter;

@Getter
public enum NginxSignal {
    RELOAD("reload"),
    REOPEN("reopen"),
    QUIT("quit"),
    STOP("stop");

    private final String signal;

    NginxSignal(String signal) {
        this.signal = signal;
    }
}
