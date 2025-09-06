package uz.sardorbroo.jinx.core.file.resolver.impl;

import org.apache.commons.lang3.StringUtils;
import uz.sardorbroo.jinx.core.file.enumeration.FileType;
import uz.sardorbroo.jinx.core.file.resolver.FileTypeResolver;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SimpleFileTypeResolver implements FileTypeResolver {

    private final Map<String, FileType> TYPES = Arrays.stream(FileType.values())
            .collect(Collectors.toMap(FileType::getName, Function.identity()));

    @Override
    public FileType resolve(String filename) {

        if (StringUtils.isBlank(filename)) return null;

        String extension = extractExtension(filename);
        return TYPES.getOrDefault(extension, FileType.OTHER);
    }

    private String extractExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
}
