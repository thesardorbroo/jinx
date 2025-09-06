package uz.sardorbroo.jinx.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.service.DirectiveSpecificationService;
import uz.sardorbroo.jinx.core.service.domain.DirectiveSpecification;
import uz.sardorbroo.jinx.core.service.dto.DirectiveSpecificationDto;
import uz.sardorbroo.jinx.core.service.mapper.DirectiveSpecificationMapper;
import uz.sardorbroo.jinx.core.service.repository.DirectiveSpecificationRepository;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DirectiveSpecificationServiceImpl implements DirectiveSpecificationService {

    private final DirectiveSpecificationRepository repository;
    private final DirectiveSpecificationMapper mapper;
    
    @Override
    public Optional<DirectiveSpecificationDto> save(DirectiveSpecificationDto conf) {
        log.info("Save new Nginx config. DirectiveSpecificationDto: {}", conf);

        if (Objects.isNull(conf)) {
            log.warn("Invalid argument has passed! DirectiveSpecificationDto must not be null!");
            return Optional.empty();
        }

        DirectiveSpecification entity = mapper.toEntity(conf);
        entity = repository.save(entity);

        DirectiveSpecificationDto savedConf = mapper.toDto(entity);

        log.info("Nginx config has saved successfully. DirectiveSpecification: {}", savedConf);
        return Optional.of(savedConf);
    }

    @Override
    public Optional<DirectiveSpecificationDto> update(DirectiveSpecificationDto conf) {
        log.info("Update existed directive specification. DirectiveSpecificationDto: {}", conf);

        if (Objects.isNull(conf) || Objects.isNull(conf.getId())) {
            log.warn("Invalid argument has passed! DirectiveSpecificationDto or ID must not be null!");
            return Optional.empty();
        }

        Optional<DirectiveSpecification> DirectiveSpecificationOptional = repository.findById(conf.getId());
        if (DirectiveSpecificationOptional.isEmpty()) {
            log.warn("Trying to update not existed Nginx config! DirectiveSpecificationDto: {}", conf);
            return Optional.empty();
        }

        DirectiveSpecification entity = mapper.toEntity(conf);
        entity = repository.save(entity);

        DirectiveSpecificationDto updatedConf = mapper.toDto(entity);

        log.info("Nginx config has updated successfully. DirectiveSpecification: {}", updatedConf);
        return Optional.of(updatedConf);
    }

    @Override
    public List<DirectiveSpecificationDto> getAll() {
        log.info("Get all Nginx configs ");

        List<DirectiveSpecificationDto> configs = repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());

        log.info("Nginx configs are fetched successfully. Nginx configs count: {}", configs.size());
        return configs;
    }

    @Override
    public Optional<DirectiveSpecificationDto> getByName(String name) {
        log.info("Get Nginx configuration by name. Name: {}", name);

        if (StringUtils.isBlank(name)) {
            log.warn("Invalid argument has passed! Nginx configuration name must not be null!");
            return Optional.empty();
        }

        Optional<DirectiveSpecificationDto> configOpt = repository.findByName(name)
                .map(mapper::toDto);

        log.info("Has Nginx configuration found by name? Result: {}", configOpt);
        return configOpt;
    }

    @Override
    public Optional<DirectiveSpecificationDto> getById(String id) {
        log.info("Get existed Nginx config by id. ID: {}", id);

        if (StringUtils.isEmpty(id)) {
            log.warn("Invalid argument has passed! Nginx config ID must not be blank!");
            return Optional.empty();
        }

        Optional<DirectiveSpecificationDto> DirectiveSpecificationOptional = repository.findById(id)
                .map(mapper::toDto);

        log.info("Is Nginx config found by ID? Result: {}", DirectiveSpecificationOptional.isPresent());
        return DirectiveSpecificationOptional;
    }

    @Override
    public Optional<DirectiveSpecificationDto> delete(String id) {
        log.info("Delete existed Nginx config by ID. ID: {}", id);

        if (StringUtils.isEmpty(id)) {
            log.warn("Invalid argument has passed! Nginx config ID must not be blank!");
            return Optional.empty();
        }

        Optional<DirectiveSpecificationDto> DirectiveSpecificationOptional = repository.findById(id)
                .map(mapper::toDto);

        repository.deleteById(id);

        log.info("Nginx config has deleted successfully. Nginx config ID: {}", id);
        return DirectiveSpecificationOptional;
    }

}
