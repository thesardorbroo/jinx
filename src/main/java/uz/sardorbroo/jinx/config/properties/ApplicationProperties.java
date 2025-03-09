package uz.sardorbroo.jinx.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {

    private Features features = new Features();

    @Data
    public static class Features {

        private Boolean packageScan;

    }
}
