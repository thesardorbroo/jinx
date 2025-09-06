package uz.sardorbroo.jinx.core.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContextDto {

    private String id;

    private String name;

    private List<DirectiveDto> directives = new ArrayList<>();

    private List<ContextDto> contexts = new ArrayList<>();

    public boolean addDirective(DirectiveDto directive) {
        return add(directive, this.directives);
    }

    public boolean removeDirective(DirectiveDto directive) {
        return remove(directive, this.directives);
    }

    public boolean addContext(ContextDto context) {
        return add(context, this.contexts);
    }

    public boolean removeContext(ContextDto context) {
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
