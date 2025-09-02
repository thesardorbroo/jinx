package uz.sardorbroo.jinx.core.content;

import uz.sardorbroo.jinx.core.content.pojo.Context;

import java.io.InputStream;

public interface ContentLoader {

    Context load(InputStream is);
}
