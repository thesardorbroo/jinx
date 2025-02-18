package uz.sardorbroo.jinx;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import uz.sardorbroo.jinx.core.file.PackageScanner;
import uz.sardorbroo.jinx.core.file.impl.TreePackageScannerImpl;
import uz.sardorbroo.jinx.core.file.pojo.PackageNode;

@SpringBootApplication
public class JinxApplication {

    public static void main(String[] args) throws JsonProcessingException {
        SpringApplication.run(JinxApplication.class, args);

        // todo move it to another test
        PackageScanner scanner = new TreePackageScannerImpl();
        PackageNode pckg = scanner.scan("src");

        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(pckg));
    }

}
