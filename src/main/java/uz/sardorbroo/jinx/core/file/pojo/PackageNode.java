package uz.sardorbroo.jinx.core.file.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageNode {

    private String name;

    private long size;

    private String path;
}
