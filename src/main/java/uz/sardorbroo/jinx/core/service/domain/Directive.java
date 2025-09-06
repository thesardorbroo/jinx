package uz.sardorbroo.jinx.core.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Directive {

    private String name;

    private List<String> values;
}
