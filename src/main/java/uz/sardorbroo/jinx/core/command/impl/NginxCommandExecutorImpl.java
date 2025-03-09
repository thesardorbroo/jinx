package uz.sardorbroo.jinx.core.command.impl;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.sardorbroo.jinx.core.command.CommandExecutor;
import uz.sardorbroo.jinx.core.command.NginxCommandExecutor;
import uz.sardorbroo.jinx.core.command.enumeration.NginxSignal;
import uz.sardorbroo.jinx.core.file.NginxScanner;
import uz.sardorbroo.jinx.pojo.Result;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Service
public class NginxCommandExecutorImpl implements NginxCommandExecutor {

    // todo get absolute path with PackageScanner/NginxExeFinder
    // For Windows
    // private static final String COMMAND = "D:/Programs/Nginx/nginx-1.27.4/nginx";

    // For Linux
    private static final String COMMAND = "/etc/nginx";

    private static final Option HELP = initHelpOption();
    private static final Option VERSION = initVersionOption();
    private static final Option TEST_AND_MORE = initTestAndMore();
    private static final Option VERSION_AND_MORE = initVersionAndMore();
    private static final Option TEST = initTest();

    private static final Map<NginxSignal, Option<NginxSignal>> SIGNALS_MAP = initSignalsMap();

    private final CommandExecutor executor;

    private String command;

    public NginxCommandExecutorImpl(NginxScanner scanner,
                                    CommandExecutor executor) {
        this.executor = executor;
        this.command = scanner.getNginxDetails().getExe();
    }


    @Override
    public Result help() {
        log.info("Get instruction about how to use Nginx");

        String[] commands = buildCommands(this.command, HELP);

        Result result = executor.execute(commands);

        log.info("Instruction command has been executed. Result: {}", result);
        return result;
    }

    @Override
    public Result getVersion() {
        log.info("Get version of Nginx");

        String[] commands = buildCommands(this.command, VERSION);

        Result result = executor.execute(commands);

        log.info("Get version command has executed. Result: {}", result);
        return result;
    }

    @Override
    public Result getVersionAndMore() {
        log.info("Get version and more of Nginx");

        String[] commands = buildCommands(this.command, VERSION_AND_MORE);

        Result result = executor.execute(commands);

        log.info("Get version and more command has executed. Result: {}", result);
        return result;
    }

    @Override
    public Result test() {
        log.info("Get test of Nginx");

        String[] commands = buildCommands(this.command, TEST);

        Result result = executor.execute(commands);

        log.info("Get test command has executed. Result: {}", result);
        return result;
    }

    @Override
    public Result testAndMore() {
        log.info("Get test and more of Nginx");

        String[] commands = buildCommands(this.command, TEST_AND_MORE);

        Result result = executor.execute(commands);

        log.info("Get test and more command has executed. Result: {}", result);
        return result;
    }

    @Override
    public Result signal(NginxSignal signal) {
        log.info("Signal to Nginx. Signal: {}", signal);

        if (Objects.isNull(signal)) {
            log.warn("Invalid argument has passed! Signal must not be null!");
            throw new IllegalArgumentException("Invalid argument has passed! Signal must not be null!");
        }

        if (!SIGNALS_MAP.containsKey(signal)) {
            log.warn("Invalid argument has passed! Signal has not found!");
            throw new IllegalArgumentException("Invalid argument has passed! Signal has not found!");
        }

        Option<NginxSignal> signalOption = SIGNALS_MAP.get(signal);

        String[] commands = buildCommands(this.command, signalOption);

        Result result = executor.execute(commands);

        log.info("Signal command has been executed. Result: {}", result);
        return result;
    }

    private <T> String[] buildCommands(String root, Option<T>... options) {
        log.info("Build single command for execute");
        log.info("Root command: {} | Options: {}", root, Arrays.toString(options));

        if (StringUtils.isEmpty(root)) {
            log.warn("Invalid argument has passed! Root command must not be null!");
            throw new IllegalArgumentException("Invalid argument has passed! Root command must not be null!");
        }

        String[] commands = new String[options.length + 1];
        commands[0] = root;
        for (int i = 0; i < options.length; i++) {
            commands[i + 1] = options[i].getAsCmd();
        }

        log.debug("Single command has build successfully. Commands count: {}", commands.length);
        return commands;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    /**
     * Generic defines type of value.
     *
     * F.e:
     *  signal option accepts only some strings, like 'stop', 'quit', 'reload' and 'reopen'.
     *  port option accepts only string, like '<exposed_port>:<internal_port>'
     */
    private static class Option<T> implements Cloneable {

        private String option;

        private String description;

        /**
         * Valuable means Option accepts any value.
         * If Option doesn't accept value then 'valuable' will be 'false'.
         * <p>
         * F.e:
         * {@code -t} - option doesn't accept value. valuable would be 'false'.
         * {@code -s} - option accepts only some specific strings. valuable would be 'true'.
         */
        private boolean valuable = true;

        /**
         * Equalizer is char which is sets between Option and value.
         * <p>
         * F.e:
         * {@code -s <signal>} - there is equalizer is " "(space)
         * {@code -X=PUT} - there is equalizer is "="(equal)
         */
        private char equalizer = ' ';

        private T value;

        // rename method
        public String getAsCmd() {

            if (!this.valuable) {
                return this.option;
            }

            return this.getOption() + this.getEqualizer() + this.getValue();
        }

        @Override
        protected Option<T> clone() {

            Option<T> clone = new Option<>();
            clone.setOption(this.getOption());
            clone.setDescription(this.getDescription());
            clone.setValue(this.getValue());
            clone.setValuable(this.valuable);

            return clone;
        }
    }

    private static Option initHelpOption() {
        return Option.builder()
                .option("-h")
                .description("Gets instruction about how to use Nginx")
                .valuable(false)
                .build();
    }

    private static Option initVersionOption() {
        return Option.builder()
                .option("-v")
                .description("Gets version of current Nginx.")
                .valuable(false)
                .build();
    }

    private static Option initVersionAndMore() {
        return Option.builder()
                .option("-V")
                .description("Gets version of current Nginx and more information.") /* Write full description */
                .valuable(false)
                .build();
    }


    private static Option initTest() {
        return Option.builder()
                .option("-t")
                .description("Test configuration of Nginx files.")
                .valuable(false)
                .build();
    }

    private static Option initTestAndMore() {
        return Option.builder()
                .option("-T")
                .description("Test configuration of Nginx files and more information") /* Write full description */
                .valuable(false)
                .build();
    }

    private static Map<NginxSignal, Option<NginxSignal>> initSignalsMap() {

        Option<NginxSignal> universal = new Option<>();
        universal.setOption("-s");
        universal.setValuable(true);

        Option<NginxSignal> stop = universal.clone();
        stop.setDescription("Write description");
        stop.setValue(NginxSignal.STOP);

        Option<NginxSignal> quit = universal.clone();
        stop.setDescription("Write description");
        stop.setValue(NginxSignal.QUIT);

        Option<NginxSignal> reload = universal.clone();
        stop.setDescription("Write description");
        stop.setValue(NginxSignal.RELOAD);

        Option<NginxSignal> reopen = universal.clone();
        stop.setDescription("Write description");
        stop.setValue(NginxSignal.REOPEN);

        return Map.of(
                NginxSignal.QUIT, quit,
                NginxSignal.STOP, stop,
                NginxSignal.RELOAD, reload,
                NginxSignal.REOPEN, reopen
        );
    }
}
