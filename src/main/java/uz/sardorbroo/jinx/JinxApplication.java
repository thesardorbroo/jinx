package uz.sardorbroo.jinx;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import uz.sardorbroo.jinx.config.CRLFLogConverter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;

@Slf4j
@SpringBootApplication
public class JinxApplication {

    private static final String SWAGGER_CUSTOM_API_PATH_KEY = "springdoc.swagger-ui.path";
    private static final String SWAGGER_DEFAULT_URI = "/swagger-ui/index.html?urls.primaryName=springdocDefault";

    private static final String APP_CUSTOM_CONTEXT_PATH_KEY = "server.servlet.context-path";
    private static final String APP_DEFAULT_CONTEXT_PATH = "/";

    @SneakyThrows
    public static void main(String[] args) {
        Environment env = SpringApplication.run(JinxApplication.class, args).getEnvironment();
        logApplicationStartup(env);
    }

    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String applicationName = env.getProperty("spring.application.name");
        String serverPort = env.getProperty("server.port");
        String hostAddress = "localhost";
        String contextPath = getContextPath(env);
        String swaggerPath = getSwaggerUri(env);
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                CRLFLogConverter.CRLF_SAFE_MARKER,
                """
    
                    ----------------------------------------------------------
                    \tApplication '{}' is running! Access URLs:
                    \tLocal: \t\t{}://localhost:{}{}
                    \tExternal: \t{}://{}:{}{}
                    \tSwagger UI: {}://localhost:{}{}
                    \tProfile(s): \t{}
                    ----------------------------------------------------------""",
                applicationName,
                protocol, serverPort, contextPath,
                protocol, hostAddress, serverPort, contextPath,
                protocol, serverPort, swaggerPath,
                env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );
    }

    private static String getContextPath(Environment env) {

        return getProperty(env, APP_CUSTOM_CONTEXT_PATH_KEY)
                .orElse(APP_DEFAULT_CONTEXT_PATH);
    }

    private static String getSwaggerUri(Environment env) {

        String contextPath = getContextPath(env);

        String swagger = getProperty(env, SWAGGER_CUSTOM_API_PATH_KEY)
                .orElse(SWAGGER_DEFAULT_URI);

        return buildApiWithContextPath(contextPath, swagger);
    }

    private static Optional<String> getProperty(Environment env, String key) {
        return Optional.ofNullable(env.getProperty(key))
                .filter(StringUtils::isNotBlank);
    }

    private static String buildApiWithContextPath(String contextPath, String api) {
        return contextPath.equals("/") ? api : contextPath + api;
    }
}
