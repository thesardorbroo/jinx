package uz.sardorbroo.jinx.core.service;

import uz.sardorbroo.jinx.core.service.dto.NginxConfDto;

import java.util.List;
import java.util.Optional;

public interface NginxConfService {

    Optional<NginxConfDto> save(NginxConfDto conf);

    Optional<NginxConfDto> update(NginxConfDto conf);

    List<NginxConfDto> getAll();

    Optional<NginxConfDto> getById(String id);

    Optional<NginxConfDto> delete(String id);

}
