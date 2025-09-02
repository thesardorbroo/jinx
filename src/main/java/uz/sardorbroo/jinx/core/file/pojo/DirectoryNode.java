package uz.sardorbroo.jinx.core.file.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.HashSet;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DirectoryNode extends PackageNode {

    private boolean isDirectory = true;

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

    @Override
    public String toString() {
        return "DirectoryNode{" +
                " name=" + this.getName() +
                ", size=" + this.getSize() +
                ", path=" + this.getPath() +
                ", isDirectory=" + isDirectory +
                ", childrenNames=" + childrenNames +
                ", children=" + (children == null ? 0: children.size()) +
                '}';
    }
}
