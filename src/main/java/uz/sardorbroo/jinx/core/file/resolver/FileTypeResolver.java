package uz.sardorbroo.jinx.core.file.resolver;

import uz.sardorbroo.jinx.core.file.enumeration.FileType;

public interface FileTypeResolver {

    FileType resolve(String filename);
}
