package uz.sardorbroo.jinx.core.file.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import uz.sardorbroo.jinx.core.file.NginxScanner;
import uz.sardorbroo.jinx.core.file.pojo.NginxDetails;

import java.util.Optional;

import static uz.sardorbroo.jinx.constans.ApplicationConstants.APPLICATION_FEATURES_PROPERTIES;
import static uz.sardorbroo.jinx.constans.ApplicationConstants.PACKAGE_SCAN_PROPERTIES;

@Slf4j
@Service
@ConditionalOnProperty(prefix = APPLICATION_FEATURES_PROPERTIES, name = PACKAGE_SCAN_PROPERTIES, havingValue = "false")
public class NginxScannerSimulate implements NginxScanner {

    @Override
    public NginxDetails getNginxDetails() {
        return null;
    }

    @Override
    public Optional<NginxDetails> detect() {
        return Optional.empty();
    }

    @Override
    public Optional<NginxDetails> detect(String path) {
        return Optional.empty();
    }

    @Override
    public Optional<NginxDetails> find() {
        return Optional.empty();
    }

    @Override
    public Optional<NginxDetails> find(String path) {
        return Optional.empty();
    }
}
