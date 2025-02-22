package uz.sardorbroo.jinx.core.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.service.NginxConfService;
import uz.sardorbroo.jinx.core.service.dto.NginxConfDto;
import uz.sardorbroo.jinx.core.service.mapper.NginxConfMapper;
import uz.sardorbroo.jinx.core.service.repository.NginxConfRepository;
import uz.sardorbroo.jinx.domain.NginxConf;

import java.util.List;
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

        NginxConf entity = mapper.toEntity(conf);
        entity = repository.save(entity);

        return Optional.of(mapper.toDto(entity));
    }

    @Override
    public Optional<NginxConfDto> update(NginxConfDto conf) {
        return Optional.empty();
    }

    @Override
    public List<NginxConfDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<NginxConfDto> getById(String id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public Optional<NginxConfDto> delete(String id) {
        return Optional.empty();
    }
}
