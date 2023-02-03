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
import pe.mil.microservices.utils.constants.LoggerConstants;
import pe.mil.microservices.utils.dtos.base.BusinessResponse;
import reactor.core.publisher.Mono;

import static pe.mil.microservices.constants.ProcessConstants.*;

@Log4j2
@RestController
public class MilitarController extends ReactorBaseController implements IMilitarController {
    private final IMilitarServices militarServices;
    private final BusinessResponse businessResponse;

    public MilitarController(IMilitarServices militarServices, BusinessResponse businessResponse) {
        super("MilitarController", businessResponse);
        this.militarServices = militarServices;
        this.businessResponse = businessResponse;
    }

    @Override
    @GetMapping(path = GET_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> findAll() {
        log.debug("method findAll initialized successfully");
        log.debug("militarControllerId {}", this.getMilitarControllerId());
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, FIND_ALL_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.getAll(), "findAll")
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error("exception error in process findAll, error: {}", throwable.getMessage()));
    }

    @Override
    @GetMapping(path = GET_MILITAR_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> getById(String militarId) {
        log.debug("method getById initialized successfully");
        log.debug("militarControllerId {}", this.getMilitarControllerId());
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, FIND_BY_ID_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.getById(militarId), "getById")
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error("exception error in process getById, error: {}", throwable.getMessage()));
    }

    @Override
    @PostMapping(value = SEARCH_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> getByDni(SearchMilitarRequest search) {
        log.debug("method getByDni initialized successfully");
        log.debug("militarControllerId {}", this.getMilitarControllerId());
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, FIND_BY_ID_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.getByDni(search.getDni()), "getByDni")
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error("exception error in process getByDni, error: {}", throwable.getMessage()));
    }

    @Override
    @PostMapping(value = SAVE_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> save(Mono<RegisterMilitarRequest> request) {
        log.debug("method save initialized successfully");
        log.debug("militarControllerId {}", this.getMilitarControllerId());
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, SAVE_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.save(request), "save");
    }

    @Override
    @PutMapping(value = UPDATE_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> update(Mono<RegisterMilitarRequest> request) {
        log.debug("method update initialized successfully");
        log.debug("militarControllerId {}", this.getMilitarControllerId());
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, UPDATE_MILITAR_LOG_METHOD);
        return super.getResponseEntity(this.militarServices.update(request), "update")
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error("exception error in process update, error: {}", throwable.getMessage()));
    }

    @Override
    @PostMapping(path = PAGES_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> page(Mono<PageMilitarRequest> request) {
        log.debug("method page initialized successfully");
        log.debug("militarControllerId {}", this.getMilitarControllerId());
        return super.getResponseEntity(this.militarServices.getPage(request), "page")
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono.just(businessResponse.getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable -> log.error("exception error in process page, error: {}", throwable.getMessage()));
    }
}
