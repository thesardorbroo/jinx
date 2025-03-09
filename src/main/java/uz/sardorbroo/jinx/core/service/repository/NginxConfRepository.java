package uz.sardorbroo.jinx.core.service.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import uz.sardorbroo.jinx.core.service.domain.NginxConf;

import java.util.List;

@Repository
public interface NginxConfRepository extends MongoRepository<NginxConf, String> {

    List<NginxConf> findByName(String name);

    @Query("{name:  '?0'}")
    List<NginxConf> findByNameWithQuery(String name);
}
