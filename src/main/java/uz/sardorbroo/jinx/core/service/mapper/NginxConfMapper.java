package uz.sardorbroo.jinx.core.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import uz.sardorbroo.jinx.core.file.pojo.FileNode;
import uz.sardorbroo.jinx.core.service.domain.NginxConf;
import uz.sardorbroo.jinx.core.service.dto.NginxConfDto;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NginxConfMapper extends EntityMapper<NginxConfDto, NginxConf> {

    NginxConfDto toDto(FileNode file);
}
