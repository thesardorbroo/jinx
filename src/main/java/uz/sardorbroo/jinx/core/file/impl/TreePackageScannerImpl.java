package uz.sardorbroo.jinx.core.file.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import uz.sardorbroo.jinx.core.file.PackageScanner;
import uz.sardorbroo.jinx.core.file.pojo.PackageNode;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class TreePackageScannerImpl implements PackageScanner {

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

        PackageNode r = convert(root); // doesn't scan if path is specific file
        if (root.isDirectory()) {
            tree(root, r);
        }

        log.info("Path has scanned. Path: {}", path);
        return r;
    }

    private void tree(File root, PackageNode node) {

        File[] files = root.listFiles();
        Set<PackageNode> children = new HashSet<>(files.length);

        for (File file : files) {

            PackageNode child = convert(file);
            if (child.isDirectory()) {
                tree(file, child);
            }
            children.add(child);
        }

        node.setChildren(children);
    }

    private PackageNode convert(File file) {

        var node = new PackageNode();

        node.setName(file.getName());
        node.setSize(file.length());
        node.setDirectory(file.isDirectory());

        return node;
    }
}
