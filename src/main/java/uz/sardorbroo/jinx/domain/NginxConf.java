package uz.sardorbroo.jinx.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * NginxConf stores content of Nginx config files
 */
@Document("nginx_conf")
public class NginxConf {

    @Id
    private String id;

    /**
     * Name of NginxConf record
     */
    private String name;

    /**
     * Real path of .conf file of Nginx
     * which is locates in nginx default directory.
     *
     * <p>F.e:</p>
     * <li>Windows - <code>C:/nginx/conf.d</code></li>
     * <li>Linux - <code>/etc/nginx/conf.d</code></li>
     */
    private String path;

    /**
     * Content of nginx config file.
     * Content of file stores here.
     *
     * <p>F.e:</p>
     * <li>http</li>
     * <li>server</li>
     * <li>domain name</li>
     */
    private String content;
}
