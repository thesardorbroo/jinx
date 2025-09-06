package uz.sardorbroo.jinx.config;

import com.github.mongobee.Mongobee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class ChangelogConfiguration {

    // @Bean
    public Mongobee configureMongobee(MongoProperties properties) {
        Mongobee runner = new Mongobee(properties.getUri());
        runner.setChangeLogsScanPackage(
                "uz.sardorbroo.jinx.config.changelog"); // the package to be scanned for changesets

        return runner;
    }
}
