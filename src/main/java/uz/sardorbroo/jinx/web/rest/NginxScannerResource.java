package uz.sardorbroo.jinx.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbroo.jinx.core.file.NginxScanner;
import uz.sardorbroo.jinx.core.file.pojo.NginxDetails;
import uz.sardorbroo.jinx.utils.ResponseUtils;

import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nginx")
public class NginxScannerResource {

    private final NginxScanner scanner;

    @GetMapping
    public ResponseEntity<NginxDetails> getNginxDetails() {
        log.info("REST request to get Nginx details");
        NginxDetails nginx = scanner.getNginxDetails();
        ResponseEntity<NginxDetails> response = ResponseEntity.ok(nginx);
        log.info("Nginx details has been fetched successfully. Response: {}", response);
        return response;
    }

    @GetMapping("/find")
    public ResponseEntity<NginxDetails> findNginxDetails(@RequestParam("path") String path) {
        log.info("REST request to find Nginx by path");
        Optional<NginxDetails> nginxOptional = scanner.find(path);
        ResponseEntity<NginxDetails> response = ResponseUtils.wrap(nginxOptional);
        log.info("Find Nginx by path flow finished. Response: {}", response);
        return response;
    }

    @PostMapping("/detect")
    public ResponseEntity<NginxDetails> detectNginx(@RequestParam("path") String path) {
        log.info("REST request to detect Nginx by path");
        Optional<NginxDetails> nginxOptional = scanner.detect(path);
        ResponseEntity<NginxDetails> response = ResponseUtils.wrap(nginxOptional);
        log.info("Detect Nginx by path flow finished. Response: {}", response);
        return response;
    }
}
