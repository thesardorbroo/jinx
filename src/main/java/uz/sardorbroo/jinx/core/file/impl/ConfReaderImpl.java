package uz.sardorbroo.jinx.core.file.impl;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.config.properties.NginxProperties;
import uz.sardorbroo.jinx.core.file.ConfReader;
import uz.sardorbroo.jinx.core.file.pojo.NginxDirective;
import uz.sardorbroo.jinx.core.helper.SafePathResolver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class ConfReaderImpl implements ConfReader {

    private String spliterator = " ";

    private final SafePathResolver resolver;
    private final String root;

    private NginxDirective context = NginxDirective.builder()
            .directive("main")
            .isBBD(true)
            .directives(new HashSet<>())
            .build();

    private NginxDirective previousContext;

    public ConfReaderImpl(NginxProperties properties) {
        this.root = properties.getHome();
        this.resolver = new SafePathResolver(root);
    }

    @Override
    public NginxDirective read(String conf) {
        log.debug("Read .conf file by given path. Path to conf: {}", conf);

        if (StringUtils.isBlank(conf)) {
            log.warn("Invalid argument has passed! Conf must not be blank!");
            throw new IllegalArgumentException("Invalid argument has passed! Conf must not be blank!");
        }

        List<NginxDirective> directives = new ArrayList<>();
        String path = resolver.build(conf);
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(path)))) {

            while (reader.ready()) {
                String line = reader.readLine().trim();

                if (StringUtils.isBlank(line) || line.startsWith("#")) {
                    continue;
                }

                String[] strings = line.split(this.spliterator);
                if (strings.length < 2) {
                    log.warn("Not enough args. Line has skipped! Line: {}", line);
                    continue;
                }

                String directive = strings[0];
                boolean isBBD = false; // BBD stands for "block-based directive"
                boolean isSLD = true;  // SLD stands for "single line directive"
                List<String> values = new ArrayList<>();
                boolean newContext = false;
                for (int i = 1; i < strings.length; i++) {

                    String target = strings[i];
                    if (StringUtils.isBlank(target)) {
                        continue;
                    }

                    if (target.equals(";")
                            || target.endsWith(";")) { // semicolon means end of single line directive
                        isBBD = false;
                        isSLD = true;
                        target = target.substring(0, target.length() - 1);
                        values.add(target);
                        continue;
                    } else if (target.equals("{")
                            || target.startsWith("{")) { // braces('{}') mean directive is block-based
                        isBBD = true;
                        isSLD = false;
                        newContext = true;
                        continue;
                    } else if (target.equals("}")
                            || target.endsWith("}")) {
                        // extract value from '}'
                        this.context = this.previousContext;
                    }

                    values.add(target);
                }

                var ngDirective = new NginxDirective();
                ngDirective.setDirective(directive);
                ngDirective.setValues(values);
                ngDirective.setBBD(isBBD);
                ngDirective.setSLD(isSLD);

                this.context.getDirectives().add(ngDirective);

                if (newContext) {
                    this.previousContext = context;
                    this.context = ngDirective;
                }
            }

        } catch (IOException e) {
            log.info("Error while reading .conf file! Exception: {}", e.getMessage());
            log.debug("Stack trace", e);
            return null;
        }

        log.info(".conf file has read successfully. Directives count: {}", directives.size());
        return this.context;
    }
}
