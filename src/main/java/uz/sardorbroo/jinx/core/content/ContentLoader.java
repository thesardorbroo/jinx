package uz.sardorbroo.jinx.core.content;

import uz.sardorbroo.jinx.core.service.dto.ContextDto;

import java.io.InputStream;

public interface ContentLoader {

    ContextDto load(InputStream is);
}
