package uz.sardorbroo.jinx.core.service.mapper;

import java.util.Collection;
import java.util.List;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    List<E> toEntity(Collection<D> dtos);

    D toDto(E entity);

    List<D> toDto(Collection<E> entities);

}
