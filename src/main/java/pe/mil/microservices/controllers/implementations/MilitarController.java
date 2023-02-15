package pe.mil.microservices.controllers.implementations;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import pe.mil.microservices.controllers.base.ReactorBaseController;
import pe.mil.microservices.controllers.contracts.IMilitarController;
import pe.mil.microservices.dto.requests.PageMilitarRequest;
import pe.mil.microservices.dto.requests.RegisterMilitarRequest;
import pe.mil.microservices.dto.requests.SearchMilitarRequest;
import pe.mil.microservices.services.contracts.IMilitarServices;
import pe.mil.microservices.utils.components.exceptions.CommonBusinessProcessException;
import pe.mil.microservices.utils.dtos.base.BusinessResponse;
import reactor.core.publisher.Mono;

import static pe.mil.microservices.constants.ProcessConstants.*;
import static pe.mil.microservices.utils.constants.LoggerConstants.*;

@Log4j2
@RestController
public class MilitarController extends ReactorBaseController implements IMilitarController {
    private final IMilitarServices militarServices;
    private final BusinessResponse businessResponse;

    public MilitarController(IMilitarServices militarServices, BusinessResponse businessResponse) {
        super(MilitarController.log.getName(), businessResponse);
        this.militarServices = militarServices;
        this.businessResponse = businessResponse;
    }

    @Override
    @GetMapping(path = GET_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> findAll() {
        log.debug(LOAD_MICROSERVICE_LOAD_METHOD_FORMAT, FIND_ALL_METHOD_NAME);
        log.debug(MICROSERVICE_CONTROLLER_ID_FORMAT, this.getMilitarControllerId());
        ThreadContext.put(CONSTANTS_LOG_METHOD, FIND_ALL_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.getAll(), FIND_ALL_METHOD_NAME)
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, FIND_ALL_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    @GetMapping(path = GET_MILITAR_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> getById(String militarId) {
        log.debug(LOAD_MICROSERVICE_LOAD_METHOD_FORMAT, FIND_BY_ID_METHOD_NAME);
        log.debug(MICROSERVICE_CONTROLLER_ID_FORMAT, this.getMilitarControllerId());
        ThreadContext.put(CONSTANTS_LOG_METHOD, FIND_BY_ID_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.getById(militarId), FIND_BY_ID_METHOD_NAME)
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, FIND_BY_ID_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    @PostMapping(value = SEARCH_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> getByDni(SearchMilitarRequest search) {
        log.debug(LOAD_MICROSERVICE_LOAD_METHOD_FORMAT, FIND_BY_DNI_METHOD_NAME);
        log.debug(MICROSERVICE_CONTROLLER_ID_FORMAT, this.getMilitarControllerId());
        ThreadContext.put(CONSTANTS_LOG_METHOD, FIND_BY_ID_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.getByDni(search.getDni()), FIND_BY_DNI_METHOD_NAME)
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, FIND_BY_DNI_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    @PostMapping(value = SAVE_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> save(Mono<RegisterMilitarRequest> request) {
        log.debug(LOAD_MICROSERVICE_LOAD_METHOD_FORMAT, SAVE_METHOD_NAME);
        log.debug(MICROSERVICE_CONTROLLER_ID_FORMAT, this.getMilitarControllerId());
        ThreadContext.put(CONSTANTS_LOG_METHOD, SAVE_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.save(request), SAVE_METHOD_NAME);
    }

    @Override
    @PutMapping(value = UPDATE_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> update(Mono<RegisterMilitarRequest> request) {
        log.debug(LOAD_MICROSERVICE_LOAD_METHOD_FORMAT, UPDATE_METHOD_NAME);
        log.debug(MICROSERVICE_CONTROLLER_ID_FORMAT, this.getMilitarControllerId());
        ThreadContext.put(CONSTANTS_LOG_METHOD, UPDATE_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.update(request), UPDATE_METHOD_NAME)
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, UPDATE_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    @PostMapping(path = PAGES_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> page(Mono<PageMilitarRequest> request) {
        log.debug(LOAD_MICROSERVICE_LOAD_METHOD_FORMAT, PAGE_METHOD_NAME);
        log.debug(MICROSERVICE_CONTROLLER_ID_FORMAT, this.getMilitarControllerId());
        ThreadContext.put(CONSTANTS_LOG_METHOD, PAGE_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.getPage(request), PAGE_METHOD_NAME)
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, PAGE_METHOD_NAME, throwable.getMessage()));
    }
}