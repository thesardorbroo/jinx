package uz.sardorbroo.jinx.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.sardorbroo.jinx.core.content.pojo.Context;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NginxConfDto {

    private String id;

    private String name;

    private String path;

    private Context context;
}
