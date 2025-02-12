package uz.sardorbroo.jinx.web.rest;

import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.sardorbroo.jinx.core.command.CommandExecutor;
import uz.sardorbroo.jinx.core.command.impl.ProcessCommandExecutor;
import uz.sardorbroo.jinx.enumeration.Command;
import uz.sardorbroo.jinx.pojo.Result;

@Slf4j
@RestController
@RequestMapping("/api/nginx")
public class NginxResource {

    @SneakyThrows
    @GetMapping("/version")
    public void getVersion(HttpServletResponse response) {
        log.info("Get Nginx version");
//        CommandExecutor executor = new ApacheCommandExecutor(System.in, response.getOutputStream(), response.getOutputStream());
        CommandExecutor executor = new ProcessCommandExecutor();
        Result result = executor.execute(Command.NGINX);
        response.getOutputStream().print(result.toString());
    }
}
