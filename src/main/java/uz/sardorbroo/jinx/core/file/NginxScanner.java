package uz.sardorbroo.jinx.core.file;

import uz.sardorbroo.jinx.core.file.pojo.NginxDetails;

import java.util.Optional;

public interface NginxScanner {

    NginxDetails getNginxDetails();

    Optional<NginxDetails> detect();

    Optional<NginxDetails> detect(String path);

    Optional<NginxDetails> find();

    Optional<NginxDetails> find(String path);

}
