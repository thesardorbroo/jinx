package uz.sardorbroo.jinx.core.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.sardorbroo.jinx.core.service.dto.NginxConfDto;
import uz.sardorbroo.jinx.domain.NginxConf;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NginxConfMapper extends EntityMapper<NginxConfDto, NginxConf> {
}
