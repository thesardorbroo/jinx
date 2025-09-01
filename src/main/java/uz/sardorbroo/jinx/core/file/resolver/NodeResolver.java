package uz.sardorbroo.jinx.core.file.resolver;

import uz.sardorbroo.jinx.core.file.pojo.PackageNode;

import java.io.File;

public interface NodeResolver {

    PackageNode resolve(File file);
}
