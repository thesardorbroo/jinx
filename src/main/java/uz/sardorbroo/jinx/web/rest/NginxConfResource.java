package uz.sardorbroo.jinx.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbroo.jinx.core.service.NginxConfService;
import uz.sardorbroo.jinx.core.service.dto.NginxConfDto;
import uz.sardorbroo.jinx.utils.ResponseUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nginx")
public class NginxConfResource {

    private final NginxConfService service;

    @PostMapping("/conf")
    public ResponseEntity<NginxConfDto> save(@Valid @RequestBody NginxConfDto dto) {
        log.info("REST request to save new NginxConf");
        Optional<NginxConfDto> result = service.save(dto);
        ResponseEntity<NginxConfDto> response = ResponseUtils.wrap(result);
        log.info("Saving new NginxConf has finished. Response: {}", response);
        return response;
    }

    @PutMapping("/conf")
    public ResponseEntity<NginxConfDto> update(@Valid @RequestBody NginxConfDto dto) {
        log.info("REST request to update existed NginxConf");
        Optional<NginxConfDto> result = service.update(dto);
        ResponseEntity<NginxConfDto> response = ResponseUtils.wrap(result);
        log.info("Updating existed NginxConf has finished. Response: {}", response);
        return response;
    }

    @GetMapping("/conf")
    public ResponseEntity<List<NginxConfDto>> getAll() {
        log.info("REST request to get all NginxConf");
        List<NginxConfDto> result = service.getAll();
        ResponseEntity<List<NginxConfDto>> response = ResponseUtils.wrap(result);
        log.info("Getting all NginxConf has finished. Response: {}", response);
        return response;
    }

    @GetMapping("/conf/{id}")
    public ResponseEntity<NginxConfDto> getById(@PathVariable String id) {
        log.info("REST request to get NginxConf by ID");
        Optional<NginxConfDto> result = service.getById(id);
        ResponseEntity<NginxConfDto> response = ResponseUtils.wrap(result);
        log.info("Getting NginxConf by ID has finished. Response: {}", response);
        return response;
    }

    @DeleteMapping("/conf/{id}")
    public ResponseEntity<NginxConfDto> delete(@PathVariable String id) {
        log.info("REST request to delete NginxConf by ID");
        Optional<NginxConfDto> result = service.delete(id);
        ResponseEntity<NginxConfDto> response = ResponseUtils.wrap(result);
        log.info("Deleting NginxConf by ID has finished. Response: {}", response);
        return response;
    }
}
