package uz.sardorbroo.jinx.core.content.resolver;

import uz.sardorbroo.jinx.core.service.dto.DirectiveDto;

public interface DirectiveResolver {

    DirectiveDto resolve(String line);
}
