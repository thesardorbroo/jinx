package uz.sardorbroo.jinx.core.service.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document("directive_specification")
public class DirectiveSpecification {

    @Id
    @Field("id")
    private String id;

    @Field("name")
    private String name;

    @Field("contexts")
    private List<String> contexts;

    @Field("possible_values")
    private String possibleValues;

    @Field("does_support_regex")
    private Boolean doesSupportRegex;

    @Field("is_block_directive")
    private Boolean isBlockDirective;

    @Field("description")
    private String description;
}
