package uz.sardorbroo.jinx.core.file.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PackageNode {

    private String name;

    private boolean isDirectory = true;

    private long size;

    private Collection<String> childrenNames = new HashSet<>();

    private Collection<PackageNode> children = new HashSet<>();

    /**
     * Children names fills automatically when you set children collection.
     *
     * @param children
     */
    public void setChildren(Collection<PackageNode> children) {
        this.children = children;

        this.childrenNames = children.stream()
                .map(PackageNode::getName)
                .collect(Collectors.toSet());
    }

    /**
     * Child name adds automatically if you add child to children collection.
     *
     * @param child
     */
    public void addChild(PackageNode child) {
        this.children.add(child);
        this.childrenNames.add(child.getName());
    }

    /**
     * Child name removes automatically if you remove child to children collection.
     *
     * @param child
     */
    public void removeChild(PackageNode child) {
        this.children.remove(child);
        this.childrenNames.remove(child.getName());
    }
}
