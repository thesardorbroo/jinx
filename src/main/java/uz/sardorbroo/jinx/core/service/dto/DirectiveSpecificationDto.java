package uz.sardorbroo.jinx.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DirectiveSpecificationDto {

    private String id;

    private String name;

    private List<String> contexts;

    private String possibleValues;

    private Boolean doesSupportRegex;

    private Boolean isBlockDirective;

    private String description;
}
