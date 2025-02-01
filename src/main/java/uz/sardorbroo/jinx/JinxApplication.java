package uz.sardorbroo.jinx;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecutor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class JinxApplication {

	public static void main(String[] args) {
		SpringApplication.run(JinxApplication.class, args);

		CommandLine cmdLine = CommandLine.parse("docker"); // todo remove it
		DefaultExecutor executor = new DefaultExecutor();
		try {
			int exitValue = executor.execute(cmdLine);
			System.out.println(exitValue);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
