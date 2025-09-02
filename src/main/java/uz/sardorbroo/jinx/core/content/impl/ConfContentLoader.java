package uz.sardorbroo.jinx.core.content.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.content.ContentLoader;
import uz.sardorbroo.jinx.core.content.directive.DirectiveStorage;
import uz.sardorbroo.jinx.core.content.pojo.BlockDirective;
import uz.sardorbroo.jinx.core.content.pojo.Context;
import uz.sardorbroo.jinx.core.content.pojo.Directive;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfContentLoader implements ContentLoader {

    private final DirectiveStorage storage;
    private final ObjectMapper mapper;

    @Override
    @SneakyThrows
    public String load(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Context main = new Context();
        main.setName("main");

        while (reader.ready()) {

            String line = reader.readLine();
            main = load(line, main, main);
        }

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(main);
    }

    private Context load(String line, Context context, Context root) {

        if (StringUtils.isBlank(line)) {
            return context;
        }

        if (!line.startsWith("#")) { // line is commented

            String optimized = line.trim();
            String[] elements = optimized.split(" ");
            if (elements.length != 0) {

                if (StringUtils.isBlank(elements[0]) || !storage.supported(elements[0])) {
                    return context;
                }

                Directive directive = new Directive();
                String name = elements[0];
                List<String> values = new ArrayList<>();

                for (int i = 1; i < elements.length; i++) {

                    String arg = elements[i];
                    if (StringUtils.isBlank(arg)) continue;

                    if (arg.startsWith("{")) {

                        Context inner = new Context();
                        inner.setName(name);

                        load("", inner, context);

                        BlockDirective block = new BlockDirective();
                        block.setValues(values);
                        block.setDirectives(new ArrayList<>());

                        context.setInner(inner);

                    } else if (arg.endsWith("}")) {
                        return root;

                    } else if (arg.endsWith(";")) {
                        arg = arg.substring(0, arg.lastIndexOf(";"));
                    }

                    values.add(arg);
                }

                directive.setName(name);
                directive.setValues(values);

                context.getDirectives().add(directive);
            }
        }

        return context;
    }
}