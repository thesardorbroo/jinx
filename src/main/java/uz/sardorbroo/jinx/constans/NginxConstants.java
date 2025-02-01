package uz.sardorbroo.jinx.constans;

import uz.sardorbroo.jinx.pojo.Flag;
import uz.sardorbroo.jinx.pojo.PossibleValue;

import java.util.Set;

public class NginxConstants {

    // Nginx default locations and files
    public static final String DEFAULT_NGINX_ROOT_LOCATION = "/etc/nginx";
    public static final String DEFAULT_NGINX_ROOT_CONFIG_FILE = "/nginx.conf";
    public static final String DEFAULT_NGINX_CONFIGURATION_FILES_LOCATION = "/conf.d";

    // Nginx flags
    /**
     * Todo. Transform all Flags to Command and make ServiceClient
     * F.e:
     *      Service client = nginx
     *      Commands = [HELP, VERSION, VERSION_AND_MORE, TEST, TEST_AND_MORE, SIGNAL]
     *
     *      Command executor executes command like that.
     *      {service client} {command} {command value} [{flags with values}]
     */
    public static final Flag HELP = Flag.builder()
            .name("-h")
            .description("Print this help message")
            .build();

    public static final Flag VERSION = Flag.builder()
            .name("-v")
            .description("Gets the version of Nginx")
            .build();

    public static final Flag VERSION_AND_MORE_INFO = Flag.builder()
            .name("-V")
            .description("Gets the version of Nginx, and build information, and configuration arguments, " +
                    "which contain modules embedded in the binary file Nginx")
            .build();

    public static final Flag TEST = Flag.builder()
            .name("-t")
            .description("Checks all Nginx configurations")
            .build();

    public static final Flag TEST_AND_MORE = Flag.builder()
            .name("-T")
            .description("Checks the NGINX configuration and displays it verified on the screen. " +
                    "This command is useful when looking for support")
            .build();

    private static final PossibleValue STOP = PossibleValue.builder()
            .value("stop")
            .description("Immediately terminates the NGINX process")
            .build();

    private static final PossibleValue QUIT = PossibleValue.builder()
            .value("quit")
            .description("Stops the process NGINX after completing in-flight request processing")
            .build();

    private static final PossibleValue RELOAD = PossibleValue.builder()
            .value("reload")
            .description("Restarts the configuration")
            .build();

    private static final PossibleValue REOPEN = PossibleValue.builder()
            .value("reopen")
            .description("Opens log files")
            .build();

    public static final Flag SIGNAL = Flag.builder()
            .name("-s")
            .possibleValues(Set.of(STOP, QUIT, RELOAD, REOPEN))
            .description("Sends a signal to the NGINX main process")
            .build();

}
