package uz.sardorbroo.jinx.core.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@Document("context")
@NoArgsConstructor
@AllArgsConstructor
public class Context {

    @Id
    private String id;

    private String name;

    private List<Directive> directives = new ArrayList<>();

    private List<Context> contexts = new ArrayList<>();

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
