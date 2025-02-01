package uz.sardorbroo.jinx.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Flag {

    private String name;

    private String fullName;

    private String description;

    private Object defaultValue;

    private Set<PossibleValue> possibleValues;

}
