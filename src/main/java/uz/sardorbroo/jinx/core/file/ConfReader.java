package uz.sardorbroo.jinx.core.file;

import uz.sardorbroo.jinx.core.file.pojo.NginxDirective;

public interface ConfReader {

    /**
     * Path to <code>.conf</code> file.
     *
     * @param conf
     * @return
     */
    NginxDirective read(String conf);
}
