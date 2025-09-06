package uz.sardorbroo.jinx.config.changelog;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.sardorbroo.jinx.core.service.DirectiveSpecificationService;
import uz.sardorbroo.jinx.core.service.dto.DirectiveSpecificationDto;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Slf4j
@ChangeLog
@Component
public class DirectiveSpecificationChangelog {

    private static final String SARDORBROO = "Sardorbroo";

    @Value("${application.directive-storage.init}")
    private String filename;

    @Autowired
    private DirectiveSpecificationService service;

    @PostConstruct
    @ChangeSet(author = SARDORBROO, order = "0", id = "2025-09-06 19:45 Save initial data of all Nginx directives")
    public void initDefaultData() {

        log.info("Initializing all Nginx directives config...");

        List<DirectiveSpecificationDto> directives = resolve(filename)
                .stream()
                .map(directive -> service.save(directive))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        log.info("All Nginx directives config has saved successfully. Nginx directives count: {}", directives.size());
        log.debug("Saved Nginx directives: {}", directives);
    }

    private List<DirectiveSpecificationDto> resolve(String path) {

        List<DirectiveSpecificationDto> directives = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {

            boolean header = true;
            while (reader.ready()) {

                String line = reader.readLine();
                if (header) {
                    header = false;
                    continue;
                }

                String[] columns = line.split(",", 6);
                DirectiveSpecificationDto directive = new DirectiveSpecificationDto();
                directive.setName(columns[0]);
                directive.setContexts(Arrays.asList(columns[1].split(",")));
                directive.setPossibleValues(columns[2]);
                directive.setDoesSupportRegex(Objects.equals("true", columns[3]));
                directive.setIsBlockDirective(Objects.equals("true", columns[4]));
                directive.setDescription(columns[5]);

                directives.add(directive);
            }

            return directives;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
