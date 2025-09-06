package uz.sardorbroo.jinx.core.file.resolver.impl;

import lombok.SneakyThrows;
import uz.sardorbroo.jinx.core.content.ContentLoader;
import uz.sardorbroo.jinx.core.content.directive.impl.MemoryDirectiveStorage;
import uz.sardorbroo.jinx.core.content.impl.ConfContentLoader;
import uz.sardorbroo.jinx.core.file.enumeration.FileType;
import uz.sardorbroo.jinx.core.file.pojo.DirectoryNode;
import uz.sardorbroo.jinx.core.file.pojo.FileNode;
import uz.sardorbroo.jinx.core.file.pojo.PackageNode;
import uz.sardorbroo.jinx.core.file.resolver.FileTypeResolver;
import uz.sardorbroo.jinx.core.file.resolver.NodeResolver;
import uz.sardorbroo.jinx.core.service.dto.ContextDto;

import java.io.File;
import java.io.FileInputStream;
import java.util.Objects;

public class SimpleNodeResolver implements NodeResolver {

    private final DirectoryConverter dirConverter = new DirectoryConverter();

    private final FileConverter fileConverter = new FileConverter();

    private final FileTypeResolver resolver = new SimpleFileTypeResolver();

    public PackageNode resolve(File file) {

        if (Objects.isNull(file)) return null;

        Converter converter = file.isDirectory() ? dirConverter : fileConverter;
        return converter.convert(file);
    }

    private interface Converter {

        PackageNode convert(File file);
    }

    private class DirectoryConverter implements Converter {

        @Override
        public PackageNode convert(File file) {

            var node = new DirectoryNode();

            node.setName(file.getName());
            node.setSize(file.length());
            node.setDirectory(file.isDirectory());
            node.setPath(file.getPath());

            return node;
        }
    }

    private class FileConverter implements Converter {

        private final ContentLoader loader = new ConfContentLoader(new MemoryDirectiveStorage());

        @SneakyThrows
        @Override
        public PackageNode convert(File file) {

            var node = new FileNode();

            node.setName(file.getName());
            node.setSize(file.length());
            node.setPath(file.getPath());

            FileType type = resolver.resolve(file.getName());
            type.getSetter().accept(node);

            if (Objects.equals(FileType.CONF, type)) {
                ContextDto context = loader.load(new FileInputStream(file));
                node.setContext(context);
            }

            return node;
        }
    }

}
