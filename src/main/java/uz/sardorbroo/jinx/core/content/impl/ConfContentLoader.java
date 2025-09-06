package uz.sardorbroo.jinx.core.content.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.content.ContentLoader;
import uz.sardorbroo.jinx.core.content.directive.DirectiveStorage;
import uz.sardorbroo.jinx.core.service.dto.BlockDirectiveDto;
import uz.sardorbroo.jinx.core.service.dto.ContextDto;
import uz.sardorbroo.jinx.core.service.dto.DirectiveDto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Stack;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConfContentLoader implements ContentLoader {

    private final DirectiveStorage storage;

    private final Stack<ContextDto> contexts = new Stack<>();

    @Override
    @SneakyThrows
    public ContextDto load(InputStream is) {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        ContextDto main = new ContextDto();
        main.setName("main");
        this.contexts.push(main);

        while (reader.ready()) {

            String line = reader.readLine();
            load(line);
        }

        return this.contexts.pop();
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
                    this.contexts.pop();
                }

                if (StringUtils.isBlank(name) || !storage.supported(name)) {
                    return;
                }

                DirectiveDto directive = new DirectiveDto();
                List<String> values = new ArrayList<>();

                for (int i = 1; i < elements.length; i++) {

                    String arg = elements[i];
                    if (StringUtils.isBlank(arg)) continue;

                    if (arg.startsWith("{")) {

                        ContextDto context = new ContextDto();
                        context.setName(name);

                        BlockDirectiveDto block = new BlockDirectiveDto();
                        block.setName(name);
                        block.setValues(values);

                        this.contexts.peek().addContext(context);
                        this.contexts.push(context);

                        break;

                    } else if (arg.endsWith("}")) {

                        this.contexts.pop();

                    } else if (arg.endsWith(";")) {
                        arg = arg.substring(0, arg.lastIndexOf(";"));
                    }

                    values.add(arg);
                }

                directive.setName(name);
                directive.setValues(values);

                if (StringUtils.isNotBlank(name)) {
                    this.contexts.lastElement().addDirective(directive);
                }
            }
        }
    }
}