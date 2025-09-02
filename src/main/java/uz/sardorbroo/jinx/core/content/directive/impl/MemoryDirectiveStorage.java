package uz.sardorbroo.jinx.core.content.directive.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.content.directive.DirectiveStorage;

import java.io.*;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryDirectiveStorage implements DirectiveStorage {

    public static final Map<String, Directive> DIRECTIVES = getDirectives();

    private static final Collection<String> DIRECTIVES_NAMES
            = Collections.unmodifiableCollection(DIRECTIVES.keySet());

    @Override
    public Collection<String> getAll() {
        return DIRECTIVES_NAMES;
    }

    @Override
    public boolean supported(String directive) {
        return DIRECTIVES.containsKey(directive);
    }

    public static Map<String, Directive> getDirectives() {

        Map<String, Directive> directives = new HashMap<>();
        String path = "src/main/resources/config/db/init-data/nginx_directives_extended.csv";
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            boolean header = true;
            while (reader.ready()) {

                String line = reader.readLine();
                if (header) {
                    header = false;
                    continue;
                }

                String[] columns = line.split(",", 6);
                Directive directive = new Directive();
                directive.setName(columns[0]);
                directive.setContexts(columns[1]);
                directive.setPossibleValues(columns[2]);
                directive.setRegexSupport(Objects.equals("true", columns[3]));
                directive.setBlockDirective(Objects.equals("true", columns[4]));
                directive.setDescription(columns[5]);

                directives.put(directive.getName(), directive);
            }

            return directives;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Directive {

        private String name;

        private String contexts;

        private String possibleValues;

        private boolean regexSupport;

        private boolean isBlockDirective;

        private String description;
    }
}
