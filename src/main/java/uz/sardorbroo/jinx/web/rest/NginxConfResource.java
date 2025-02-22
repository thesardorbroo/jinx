package uz.sardorbroo.jinx.web.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sardorbroo.jinx.core.service.NginxConfService;
import uz.sardorbroo.jinx.core.service.dto.NginxConfDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/nginx")
public class NginxConfResource {

    private final NginxConfService service;

    @PostMapping("/conf")
    public ResponseEntity<NginxConfDto> save(@Valid @RequestBody NginxConfDto dto) {
        return ResponseEntity.ok(service.save(dto).get());
    }

    @GetMapping("/conf")
    public ResponseEntity<List<NginxConfDto>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @PostMapping("/conf/{id}")
    public ResponseEntity<NginxConfDto> getById(@PathVariable String id) {
        return ResponseEntity.ok(service.getById(id).get());
    }
}
