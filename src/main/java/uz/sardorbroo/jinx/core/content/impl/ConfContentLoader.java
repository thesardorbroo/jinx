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
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfContentLoader implements ContentLoader {

    private final DirectiveStorage storage;
    private final ObjectMapper mapper;

    // contexts
    private Context previous;
    private Context current;

    @Override
    @SneakyThrows
    public Context load(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        Context main = new Context();
        main.setName("main");
        this.current = main;

        while (reader.ready()) {

            String line = reader.readLine();
            load(line);
        }

        return main;
    }

    private void load(String line) {

        if (StringUtils.isBlank(line)) {
            return;
        }

        if (!line.startsWith("#")) { // line is commented

            String optimized = line.trim();
            String[] elements = optimized.split(" ");
            if (elements.length != 0) {

                String name = elements[0];
                if (Objects.equals("}", name)) {
                    this.current = this.previous;
                    this.previous = null;
                }

                if (StringUtils.isBlank(name) || !storage.supported(name)) {
                    return;
                }

                Directive directive = new Directive();
                List<String> values = new ArrayList<>();

                for (int i = 1; i < elements.length; i++) {

                    String arg = elements[i];
                    if (StringUtils.isBlank(arg)) continue;

                    if (arg.startsWith("{")) {

                        Context context = new Context();
                        context.setName(name);

                        BlockDirective block = new BlockDirective();
                        block.setName(name);
                        block.setValues(values);

                        this.current.addDirective(block);
                        this.current.addContext(context);

                        this.previous = this.current;
                        this.current = context;

                        break;

                    } else if (arg.endsWith("}")) {

                        this.current = this.previous;
                        this.previous = null;

                        // break;

                    } else if (arg.endsWith(";")) {
                        arg = arg.substring(0, arg.lastIndexOf(";"));
                    }

                    values.add(arg);
                }

                directive.setName(name);
                directive.setValues(values);

                if (Objects.nonNull(name)) {
                    this.current.addDirective(directive);
                }
            }
        }
    }
}