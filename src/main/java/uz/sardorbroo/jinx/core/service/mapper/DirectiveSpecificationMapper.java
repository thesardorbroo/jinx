package uz.sardorbroo.jinx.core.service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.sardorbroo.jinx.core.service.domain.DirectiveSpecification;
import uz.sardorbroo.jinx.core.service.dto.DirectiveSpecificationDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface DirectiveSpecificationMapper extends EntityMapper<DirectiveSpecificationDto, DirectiveSpecification> {
}
