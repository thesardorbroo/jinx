package uz.sardorbroo.jinx.core.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uz.sardorbroo.jinx.core.service.domain.DirectiveSpecification;

import java.util.Optional;

@Repository
public interface DirectiveSpecificationRepository extends MongoRepository<DirectiveSpecification, String> {

    @Query
    Optional<DirectiveSpecification> findByName(String name);
}
