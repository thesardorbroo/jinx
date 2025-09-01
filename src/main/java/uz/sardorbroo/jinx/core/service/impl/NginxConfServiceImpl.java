package uz.sardorbroo.jinx.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.file.pojo.DirectoryNode;
import uz.sardorbroo.jinx.core.file.pojo.FileNode;
import uz.sardorbroo.jinx.core.file.pojo.PackageNode;
import uz.sardorbroo.jinx.core.service.NginxConfService;
import uz.sardorbroo.jinx.core.service.domain.NginxConf;
import uz.sardorbroo.jinx.core.service.dto.NginxConfDto;
import uz.sardorbroo.jinx.core.service.mapper.NginxConfMapper;
import uz.sardorbroo.jinx.core.service.repository.NginxConfRepository;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NginxConfServiceImpl implements NginxConfService {

    private final NginxConfRepository repository;
    private final NginxConfMapper mapper;

    @Override
    public List<NginxConfDto> save(PackageNode node) {
        log.info("Save Nginx configurations. PackageNode: {}", node);

        if (Objects.isNull(node)) {
            log.warn("Invalid argument has passed! Nginx configuration (PackageNode) must not be null!");
            return Collections.emptyList();
        }

        List<NginxConfDto> configs = new ArrayList<>();
        save(node, configs);

        log.info("Nginx configurations are saved successfully. Saved Nginx configurations count: {}", configs.size());
        return configs;
    }

    private void save(PackageNode node, List<NginxConfDto> configs) {
        log.info("Save all Nginx configurations which is located in dir. PackageNode: {} | Configs count: {}", node, configs.size());

        if (node instanceof DirectoryNode dir) {

            for (PackageNode child : dir.getChildren()) {
                save(child, configs);
            }

        } else if (node instanceof FileNode file && file.isConf()) {

            NginxConfDto config = mapper.toDto(file);
            Optional<NginxConfDto> configOpt = getByName(config.getName());
            configOpt.ifPresentOrElse(conf -> {

                // updates existed config file
                Optional<NginxConfDto> updatedConfOpt = update(conf);
                configs.add(updatedConfOpt.orElse(null));
            }, () -> {

                // saves new config file
                Optional<NginxConfDto> savedConfOpt = save(config);
                configs.add(savedConfOpt.orElse(null));
            });

            if (configOpt.isEmpty()) {
                log.warn("Something went wrong while saving Nginx configuration! FileNode: {}", file);
            }

            configs.add(config);
        }
    }

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
    public Optional<NginxConfDto> getByName(String name) {
        log.info("Get Nginx configuration by name. Name: {}", name);

        if (StringUtils.isBlank(name)) {
            log.warn("Invalid argument has passed! Nginx configuration name must not be null!");
            return Optional.empty();
        }

        Optional<NginxConfDto> configOpt = repository.findByName(name)
                .map(mapper::toDto);

        log.info("Has Nginx configuration found by name? Result: {}", configOpt);
        return configOpt;
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
