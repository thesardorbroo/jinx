package uz.sardorbroo.jinx.core.content.directive;

import java.util.Collection;

public interface DirectiveStorage {

    Collection<String> getAll();

    boolean supported(String directive);
}
