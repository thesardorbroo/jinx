package uz.sardorbroo.jinx;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JinxApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(JinxApplication.class, args);
    }

}
