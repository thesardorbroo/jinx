package uz.sardorbroo.jinx.core.file.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FileNode extends PackageNode {

    private boolean isConf;

    private boolean isExe;

    private boolean isMd;

    private boolean isLog;

    private boolean isOther;
}
