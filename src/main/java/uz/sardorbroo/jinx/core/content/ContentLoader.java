package uz.sardorbroo.jinx.core.content;

import uz.sardorbroo.jinx.core.content.pojo.Content;

import java.io.InputStream;

public interface ContentLoader {

    Content load(InputStream is);
}
