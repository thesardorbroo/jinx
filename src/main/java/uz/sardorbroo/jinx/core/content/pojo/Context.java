package uz.sardorbroo.jinx.core.content.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Context {

    private String name;

    private List<Context> contexts = new ArrayList<>();

    private List<Directive> directives = new ArrayList<>();

    public boolean addDirective(Directive directive) {
        return add(directive, this.directives);
    }

    public boolean removeDirective(Directive directive) {
        return remove(directive, this.directives);
    }

    public boolean addContext(Context context) {
        return add(context, this.contexts);
    }

    public boolean removeContext(Context context) {
        return remove(context, this.contexts);
    }

    private <T> boolean add(T obj, Collection<T> collection) {

        if (Objects.isNull(obj)) {
            return false;
        }

        return collection.add(obj);
    }

    private <T> boolean remove(T obj, Collection<T> collection) {

        if (Objects.isNull(obj)) {
            return false;
        }

        return collection.remove(obj);
    }
}
