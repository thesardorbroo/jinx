package uz.sardorbroo.jinx.core.service;

import uz.sardorbroo.jinx.core.service.dto.DirectiveSpecificationDto;

import java.util.List;
import java.util.Optional;

public interface DirectiveSpecificationService {

    Optional<DirectiveSpecificationDto> save(DirectiveSpecificationDto conf);

    Optional<DirectiveSpecificationDto> update(DirectiveSpecificationDto conf);

    List<DirectiveSpecificationDto> getAll();

    Optional<DirectiveSpecificationDto> getByName(String name);

    Optional<DirectiveSpecificationDto> getById(String id);

    Optional<DirectiveSpecificationDto> delete(String id);
}
