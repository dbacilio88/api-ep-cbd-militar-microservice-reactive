package pe.mil.microservices.services.base;

import lombok.extern.log4j.Log4j2;
import java.util.UUID;
import static pe.mil.microservices.utils.constants.LoggerConstants.LOAD_MICROSERVICE_SUCCESSFULLY_FORMAT;
import static pe.mil.microservices.utils.constants.LoggerConstants.MICROSERVICE_SERVICE_NAME_FORMAT;

@Log4j2
public class BaseReactorService {

    private final String militarServiceId;

    public BaseReactorService(final String serviceName) {
        this.militarServiceId = UUID.randomUUID().toString();
        log.debug(MICROSERVICE_SERVICE_NAME_FORMAT, serviceName, militarServiceId);
        log.debug(LOAD_MICROSERVICE_SUCCESSFULLY_FORMAT, serviceName);
    }

    public String getMilitarServiceId() {
        return this.militarServiceId;
    }
}
