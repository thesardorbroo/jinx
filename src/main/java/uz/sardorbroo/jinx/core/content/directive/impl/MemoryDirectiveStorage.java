package uz.sardorbroo.jinx.core.content.directive.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.content.directive.DirectiveStorage;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemoryDirectiveStorage implements DirectiveStorage {

    public static final Map<String, Directive> DIRECTIVES = Map.ofEntries(
            // --- Core contexts ---
            Map.entry("main", new Directive("main", true)),
            Map.entry("events", new Directive("events", true)),
            Map.entry("http", new Directive("http", true)),
            Map.entry("server", new Directive("server", true)),
            Map.entry("location", new Directive("location", true)),
            Map.entry("upstream", new Directive("upstream", true)),

            // --- HTTP server ---
            Map.entry("listen", new Directive("listen", false)),
            Map.entry("server_name", new Directive("server_name", false)),
            Map.entry("root", new Directive("root", false)),
            Map.entry("index", new Directive("index", false)),
            Map.entry("error_page", new Directive("error_page", false)),

            // --- Proxy ---
            Map.entry("proxy_pass", new Directive("proxy_pass", false)),
            Map.entry("proxy_set_header", new Directive("proxy_set_header", false)),
            Map.entry("proxy_redirect", new Directive("proxy_redirect", false)),

            // --- SSL/TLS ---
            Map.entry("ssl_certificate", new Directive("ssl_certificate", false)),
            Map.entry("ssl_certificate_key", new Directive("ssl_certificate_key", false)),
            Map.entry("ssl_protocols", new Directive("ssl_protocols", false)),

            // --- Gzip ---
            Map.entry("gzip", new Directive("gzip", false)),
            Map.entry("gzip_types", new Directive("gzip_types", false)),

            // --- Rewrite ---
            Map.entry("rewrite", new Directive("rewrite", false)),
            Map.entry("return", new Directive("return", false)),

            // --- Logging ---
            Map.entry("access_log", new Directive("access_log", false)),
            Map.entry("error_log", new Directive("error_log", false)),

            // --- Logging ---
            Map.entry("worker_processes", new Directive("worker_processes", false))
    );

    private static final Collection<String> DIRECTIVES_NAMES
            = Collections.unmodifiableCollection(DIRECTIVES.keySet());

    @Override
    public Collection<String> getAll() {
        return DIRECTIVES_NAMES;
    }

    @Override
    public boolean supported(String directive) {
        return DIRECTIVES.containsKey(directive);
    }

    public static class Directive {
        private final String name;
        private final boolean block;

        public Directive(String name, boolean block) {
            this.name = name;
            this.block = block;
        }

        public String getName() {
            return name;
        }

        public boolean isBlock() {
            return block;
        }

        @Override
        public String toString() {
            return "Directive{name='" + name + "', block=" + block + '}';
        }
    }
}
