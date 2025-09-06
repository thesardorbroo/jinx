package uz.sardorbroo.jinx.core.content.directive.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.content.directive.DirectiveStorage;
import uz.sardorbroo.jinx.core.service.DirectiveSpecificationService;
import uz.sardorbroo.jinx.core.service.dto.DirectiveSpecificationDto;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "application.directive-storage", name = "impl", havingValue = "mongodb")
public class MongoDbDirectiveStorage implements DirectiveStorage {

    private final DirectiveSpecificationService service;
    private final Map<String, DirectiveSpecificationDto> map = new HashMap<>();

    @PostConstruct
    public void init() {
        service.getAll()
                .forEach(directive -> map.put(directive.getName(), directive));
    }

    @Override
    public Collection<String> getAll() {
        log.info("Get all directives names from MongoDB");
        Set<String> names = this.map.keySet();

        log.info("All directives names has collected from MongoDB. Names count: {}", names.size());
        log.debug("Directives names: {}", names);
        return names;
    }

    @Override
    public boolean supported(String directive) {
        log.info("Check given directive name has supported. Directive name: {}", directive);

        if (StringUtils.isBlank(directive)) {
            log.warn("Invalid argument has passed! Directive name must not be blank!");
            return false;
        }

        boolean result = map.containsKey(directive);
        log.info("Has given directive supported? Result: {}", result);
        return result;
    }
}
