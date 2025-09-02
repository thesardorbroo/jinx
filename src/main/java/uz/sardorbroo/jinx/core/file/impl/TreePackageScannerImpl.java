package uz.sardorbroo.jinx.core.file.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import uz.sardorbroo.jinx.core.file.PackageScanner;
import uz.sardorbroo.jinx.core.file.pojo.DirectoryNode;
import uz.sardorbroo.jinx.core.file.pojo.PackageNode;
import uz.sardorbroo.jinx.core.file.resolver.NodeResolver;
import uz.sardorbroo.jinx.core.file.resolver.impl.SimpleNodeResolver;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class TreePackageScannerImpl implements PackageScanner {

    private final NodeResolver resolver = new SimpleNodeResolver();

    @Override
    public PackageNode scan(String path) {
        log.info("Scan package and return as tree. Path: {}", path);

        if (StringUtils.isEmpty(path)) {
            log.warn("Invalid argument has passed! Path must not be empty!");
            throw new IllegalArgumentException("Invalid argument has passed! Path must not be empty!");
        }

        File pathAsfile = new File(path);
        if (!pathAsfile.exists()) {
            log.warn("Invalid argument has passed! Path must be exist!");
            throw new IllegalArgumentException("Invalid argument has passed! Path must be exist!");
        }

        if (pathAsfile.isFile()) {
            log.info("Given path is belong to specific file. Path: {}", path);

            String parentPath = pathAsfile.getParent();
            log.info("Scans parent of path. Parent path: {}", parentPath);

            return scan(parentPath);
        }

        PackageNode root = resolver.resolve(pathAsfile);
        if (pathAsfile.isDirectory()) {
            tree(pathAsfile, root);
        }

        log.info("Path has scanned. Path: {}", path);
        return root;
    }

    private void tree(File root, PackageNode node) {

        File[] files = root.listFiles();
        Set<PackageNode> children = new HashSet<>(files.length);

        for (File file : files) {

            PackageNode child = resolver.resolve(file);
            if (child instanceof DirectoryNode dir) {
                if (dir.isDirectory()) {
                    tree(file, dir);
                }
            }
            children.add(child);
        }

        if (node instanceof DirectoryNode dir) {

            dir.setChildren(children);
        }
    }
}
