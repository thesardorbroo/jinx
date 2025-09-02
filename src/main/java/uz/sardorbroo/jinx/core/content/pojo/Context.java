package uz.sardorbroo.jinx.core.content.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Context {

    private String name;

    private Context inner;

    private List<Directive> directives = new ArrayList<>();
}
