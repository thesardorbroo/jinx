package uz.sardorbroo.jinx.core.file.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NginxDirective {

    private String directive;

    private boolean isBBD;

    private boolean isSLD;

    private List<String> values = new ArrayList<>();

    private Set<NginxDirective> directives = new HashSet<>();
}
