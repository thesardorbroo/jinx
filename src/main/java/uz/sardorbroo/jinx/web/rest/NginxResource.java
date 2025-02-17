package uz.sardorbroo.jinx.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbroo.jinx.core.command.NginxCommandExecutor;
import uz.sardorbroo.jinx.core.command.enumeration.NginxSignal;
import uz.sardorbroo.jinx.pojo.Result;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nginx")
public class NginxResource {

    private final NginxCommandExecutor nginx;

    @SneakyThrows
    @GetMapping("/help")
    public ResponseEntity<?> help() {
        log.info("Get Nginx instruction");
        Result result = nginx.help();
        return ResponseEntity.ok(result);
    }

    @SneakyThrows
    @GetMapping("/version")
    public ResponseEntity<?> getVersion() {
        log.info("Get Nginx version");
        Result result = nginx.getVersion();
        return ResponseEntity.ok(result);
    }

    @SneakyThrows
    @GetMapping("/version/more")
    public ResponseEntity<?> getVersionAndMore() {
        log.info("Get Nginx version and more");
        Result result = nginx.getVersionAndMore();
        return ResponseEntity.ok(result);
    }

    @SneakyThrows
    @GetMapping("/test")
    public ResponseEntity<?> test() {
        log.info("Test Nginx configuration");
        Result result = nginx.test();
        return ResponseEntity.ok(result);
    }

    @SneakyThrows
    @GetMapping("/test/more")
    public ResponseEntity<?> testAndMore() {
        log.info("Test Nginx configuration and more");
        Result result = nginx.testAndMore();
        return ResponseEntity.ok(result);
    }

    @SneakyThrows
    @PostMapping("/signal")
    public ResponseEntity<?> signal(@RequestParam("signal")NginxSignal signal) {
        log.info("Send Nginx signal");
        Result result = nginx.signal(signal);
        return ResponseEntity.ok(result);
    }
}
