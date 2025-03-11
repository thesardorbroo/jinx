package uz.sardorbroo.jinx.core.helper;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

/**
 * Use {@link SafePathResolver} class for building paths safely.
 * <p>
 *     Note: Now {@link SafePathResolver} works with only file paths,
 *     it cannot work with query parameters. Be careful!
 * </p>
 *
 * <p> Todo:</p>
 * <li>
 *     <ul>Testing {@link SafePathResolver}</ul>
 *     <ul>Implementing for HTTP/HTTPS/JDBC/AMQP/WS URIs</ul>
 * </li>
 *
 * <p>
 *     Note: There is {@see UriComponentBuilder} for building
 *     HTTP/HTTPS URIs for building paths safely
 * </p>
 */
@Getter
public class SafePathResolver {

    private static final String CONNECTOR = "/";
    private final String root;

    public SafePathResolver(String root) {

        if (StringUtils.isBlank(root)) {
            throw new IllegalArgumentException("Root path cannot be blank!");
        }

        this.root = removeSlashFromEnd(root);
    }

    /**
     * Method adds slash(<code>/</code>) to begging of path,
     * and removes slash(<code>/</code>) from end of path
     *
     * <p>For example:</p>
     * <li>
     *     <code>root</code> is equal to <code>"/etc/nginx"</code>
     *     <code>path</code> is equal to <code>"conf.d/"</code>
     *     Result will be: <code>/etc/nginx/conf.d</code>
     * </li>
     * @param path
     * @return
     */
    private String slashOnlyInFirst(String path) {

        if (!path.startsWith(CONNECTOR)) {
            path = CONNECTOR + path;
        }
        path = removeSlashFromEnd(path);

        return path;
    }

    private String removeSlashFromEnd(String path) {
        return path.endsWith(CONNECTOR)
                ? path.substring(0, path.length() - 1)
                : path;
    }

    /**
     * Builds full path from arguments and root.
     *
     * @param paths
     * @return
     */
    public String build(String... paths) {

        StringBuilder builder = new StringBuilder(this.root);
        for (String path : paths) {
            builder.append(slashOnlyInFirst(path));
        }

        return builder.toString();
    }
}
