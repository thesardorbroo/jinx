package uz.sardorbroo.jinx.utils;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class ResponseUtils {

    public static <T>ResponseEntity<T> wrap(Optional<T> opt) {
        return opt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
