package uz.sardorbroo.jinx.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectiveDto {

    private String name;

    private List<String> values;
}
