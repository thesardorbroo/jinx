package uz.sardorbroo.jinx.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.sardorbroo.jinx.core.service.NginxConfService;
import uz.sardorbroo.jinx.core.service.dto.NginxConfDto;
import uz.sardorbroo.jinx.core.service.mapper.NginxConfMapper;
import uz.sardorbroo.jinx.core.service.repository.NginxConfRepository;
import uz.sardorbroo.jinx.domain.NginxConf;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NginxConfServiceImpl implements NginxConfService {

    private final NginxConfRepository repository;
    private final NginxConfMapper mapper;

    @Override
    public Optional<NginxConfDto> save(NginxConfDto conf) {
        log.info("Save new Nginx config. NginxConfDto: {}", conf);

        if (Objects.isNull(conf)) {
            log.warn("Invalid argument has passed! NginxConfDto must not be null!");
            return Optional.empty();
        }

        NginxConf entity = mapper.toEntity(conf);
        entity = repository.save(entity);

        NginxConfDto savedConf = mapper.toDto(entity);

        log.info("Nginx config has saved successfully. NginxConf: {}", savedConf);
        return Optional.of(savedConf);
    }

    @Override
    public Optional<NginxConfDto> update(NginxConfDto conf) {
        log.info("Update existed Nginx config. NginxConfDto: {}", conf);

        if (Objects.isNull(conf) || Objects.isNull(conf.getId())) {
            log.warn("Invalid argument has passed! NginxConfDto or ID must not be null!");
            return Optional.empty();
        }

        Optional<NginxConf> nginxConfOptional = repository.findById(conf.getId());
        if (nginxConfOptional.isEmpty()) {
            log.warn("Trying to update not existed Nginx config! NginxConfDto: {}", conf);
            return Optional.empty();
        }

        NginxConf entity = mapper.toEntity(conf);
        entity = repository.save(entity);

        NginxConfDto updatedConf = mapper.toDto(entity);

        log.info("Nginx config has updated successfully. NginxConf: {}", updatedConf);
        return Optional.of(updatedConf);
    }

    @Override
    public List<NginxConfDto> getAll() {
        log.info("Get all Nginx configs ");

        List<NginxConfDto> configs = repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        log.info("Nginx configs are fetched successfully. Nginx configs count: {}", configs.size());
        return configs;
    }

    @Override
    public Optional<NginxConfDto> getById(String id) {
        log.info("Get existed Nginx config by id. ID: {}", id);

        if (StringUtils.isEmpty(id)) {
            log.warn("Invalid argument has passed! Nginx config ID must not be blank!");
            return Optional.empty();
        }

        Optional<NginxConfDto> nginxConfOptional = repository.findById(id)
                .map(mapper::toDto);

        log.info("Is Nginx config found by ID? Result: {}", nginxConfOptional.isPresent());
        return nginxConfOptional;
    }

    @Override
    public Optional<NginxConfDto> delete(String id) {
        log.info("Delete existed Nginx config by ID. ID: {}", id);

        if (StringUtils.isEmpty(id)) {
            log.warn("Invalid argument has passed! Nginx config ID must not be blank!");
            return Optional.empty();
        }

        Optional<NginxConfDto> nginxConfOptional = repository.findById(id)
                .map(mapper::toDto);

        repository.deleteById(id);

        log.info("Nginx config has deleted successfully. Nginx config ID: {}", id);
        return nginxConfOptional;
    }
}
