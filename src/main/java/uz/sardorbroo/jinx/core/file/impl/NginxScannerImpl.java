package uz.sardorbroo.jinx.core.file.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import uz.sardorbroo.jinx.config.properties.NginxProperties;
import uz.sardorbroo.jinx.constans.NginxConstants;
import uz.sardorbroo.jinx.core.file.NginxScanner;
import uz.sardorbroo.jinx.core.file.PackageScanner;
import uz.sardorbroo.jinx.core.file.pojo.NginxDetails;
import uz.sardorbroo.jinx.core.file.pojo.PackageNode;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
public class NginxScannerImpl implements NginxScanner {

    private final NginxProperties properties;
    private final PackageScanner scanner;

    private NginxDetails nginx;

    public NginxScannerImpl(NginxProperties properties,
                            PackageScanner scanner) {
        this.properties = properties;
        this.scanner = scanner;

        this.nginx = find()
                .orElseThrow(() -> {
                    log.error("Nginx has not found! Path: {}", properties.getHome());
                    return new RuntimeException("Nginx has not found! Path: " + properties.getHome());
                });
    }

    @Override
    public NginxDetails getNginxDetails() {
        return this.nginx;
    }

    private void setNginx(NginxDetails nginx) {
        this.nginx = nginx;
    }

    @Override
    public Optional<NginxDetails> detect() {
        log.info("Detecting Nginx in local by default path. Path: {}", properties.getHome());

        Optional<NginxDetails> nginxOptional = find();
        nginxOptional.ifPresent(nginx -> {
            log.info("Nginx has detected successfully by default path and replaced. Nginx details: {}", nginx);

            setNginx(nginx);
        });

        return nginxOptional;
    }

    @Override
    public Optional<NginxDetails> detect(String path) {
        log.info("Detecting Nginx in local by path. Path: {}", path);

        Optional<NginxDetails> nginxOptional = find(path);
        nginxOptional.ifPresent(nginx -> {
            log.info("Nginx has detected successfully by path and replaced. Nginx details: {}", nginx);

            setNginx(nginx);
        });

        return nginxOptional;
    }

    @Override
    public Optional<NginxDetails> find() {
        log.info("Finding Nginx in local by default path. Path: {}", properties.getHome());

        return find(properties.getHome());
    }

    @Override
    public Optional<NginxDetails> find(String path) {
        log.info("Find Nginx in local by path. Path: {}", path);

        if (StringUtils.isEmpty(path)) {
            log.warn("Invalid argument has passed! Nginx home path must not be blank!");
            return Optional.empty();
        }

        PackageNode home = scanner.scan(path);
        if (Objects.isNull(home)) {
            log.warn("Nginx package has not found! Path: {}", properties.getHome());
            return Optional.empty();
        }

        boolean isExeExist = home.getChildrenNames()
                .stream()
                .anyMatch(child -> Objects.equals(NginxConstants.NGINX_EXE_NAME, child));
        if (!isExeExist) {
            log.warn("Nginx executable file is not exist in path! Path: {}", path);
            return Optional.empty();
        }

        NginxDetails nginx = new NginxDetails();
        nginx.setHome(path);
        nginx.setExe(String.join("/", path, NginxConstants.NGINX_COMMAND));
        nginx.setNode(home);

        log.info("Nginx has detected successfully. Nginx details: {}", nginx);
        return Optional.of(nginx);
    }

}
