package uz.sardorbroo.jinx.config;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Slf4j
@Configuration
public class ChangelogConfiguration {

    //@Bean
    public SpringLiquibase liquibase(DataSource ds) {
        SpringLiquibase lb = new SpringLiquibase();
        lb.setDataSource(ds);
        lb.setChangeLog("classpath:config/db/master.xml");
        lb.setContexts("dev,prod");
        lb.setShouldRun(true);
        return lb;
    }
}
