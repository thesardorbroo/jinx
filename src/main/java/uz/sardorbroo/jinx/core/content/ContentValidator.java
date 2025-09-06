package uz.sardorbroo.jinx.core.content;

import uz.sardorbroo.jinx.core.service.dto.DirectiveDto;

public interface ContentValidator {

    Void validate(DirectiveDto directive);
}
