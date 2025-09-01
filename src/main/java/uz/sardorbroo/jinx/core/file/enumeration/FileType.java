package uz.sardorbroo.jinx.core.file.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uz.sardorbroo.jinx.core.file.pojo.FileNode;

import java.util.function.Consumer;

@Getter
@AllArgsConstructor
public enum FileType {

    EXE("exe", "Executable file", node -> node.setExe(true)),
    CONF("conf", "Configuration file", node -> node.setConf(true)),
    MD("md", "Markdown file (README.md)", node -> node.setMd(true)),
    LOG("log", "Log file", node -> node.setLog(true)),
    OTHER("other", "Other file", node -> node.setOther(true)),
    ;

    private final String name;
    private final String description;
    private final Consumer<FileNode> setter;
}
