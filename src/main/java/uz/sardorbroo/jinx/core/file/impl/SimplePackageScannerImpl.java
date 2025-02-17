package uz.sardorbroo.jinx.core.file.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import uz.sardorbroo.jinx.core.file.PackageScanner;
import uz.sardorbroo.jinx.core.file.pojo.PackageNode;

import java.io.File;

@Slf4j
public class SimplePackageScannerImpl implements PackageScanner {

    @Override
    public PackageNode scan(String path) {
        log.info("Scan package and return as tree. Path: {}", path);

        if (StringUtils.isEmpty(path)) {
            log.warn("Invalid argument has passed! Path must not be empty!");
            throw new IllegalArgumentException("Invalid argument has passed! Path must not be empty!");
        }

        File root = new File(path);
        if (!root.exists()) {
            log.warn("Invalid argument has passed! Path must be exist!");
            throw new IllegalArgumentException("Invalid argument has passed! Path must be exist!");
        }

        if (root.isDirectory()) {

            // todo build package hierarchy
        }

        return null;
    }
}
