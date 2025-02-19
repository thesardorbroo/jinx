package uz.sardorbroo.jinx.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static uz.sardorbroo.jinx.constans.NginxConstants.DEFAULT_NGINX_ROOT_LOCATION;

@Data
@Configuration
@ConfigurationProperties(prefix = "nginx")
public class NginxProperties {

    /**
     * <p>Field means Nginx home path is not default</p>
     *
     * <p>F.e:</p>
     *      <li><b>Linux</b> Nginx default home path is {@code /etc/home}</li>
     *      <li><b>Windows</b> Nginx default home path is {@code C:/another_path/set_real_default_path}</li>
     */
    private String home = DEFAULT_NGINX_ROOT_LOCATION;

}
