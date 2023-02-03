package pe.mil.microservices.services.base;

import com.google.gson.Gson;
import lombok.extern.log4j.Log4j2;

import java.util.UUID;

@Log4j2
public class BaseReactorService {

    private final String militarServiceId;

    private final Gson gson;

    public BaseReactorService(final String serviceName) {
        this.gson = new Gson();
        this.militarServiceId = UUID.randomUUID().toString();
        log.debug("services name {}, militarServiceId {}", serviceName, militarServiceId);
        log.debug("{} loaded successfully", serviceName);
        log.info("gson {}", gson.toJson("BaseReactorService"));
    }

    public String getMilitarServiceId() {
        return this.militarServiceId;
    }
}
