package uz.sardorbroo.jinx.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BlockDirectiveDto extends DirectiveDto {

    private List<DirectiveDto> directives;
}
