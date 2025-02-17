package uz.sardorbroo.jinx.core.file;

import uz.sardorbroo.jinx.core.file.pojo.PackageNode;

public interface PackageScanner {

    PackageNode scan(String path);

}
