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
import pe.mil.microservices.controllers.contracts.IMilitarController;
import pe.mil.microservices.dto.Militar;
import pe.mil.microservices.dto.requests.PageMilitarRequest;
import pe.mil.microservices.dto.requests.RegisterMilitarRequest;
import pe.mil.microservices.dto.requests.SearchMilitarRequest;
import pe.mil.microservices.services.contracts.IMilitarServices;
import pe.mil.microservices.utils.components.exceptions.CommonBusinessProcessException;
import pe.mil.microservices.utils.constants.LoggerConstants;
import pe.mil.microservices.utils.dtos.base.BusinessResponse;
import pe.mil.microservices.utils.dtos.base.PageableBusinessResponse;
import reactor.core.publisher.Mono;

import java.util.UUID;

import static pe.mil.microservices.constants.ProcessConstants.*;

@Log4j2
@RestController
public class MilitarController implements IMilitarController {
    private final IMilitarServices militarServices;
    private final BusinessResponse businessResponse;
    private final String militarControllerId;

    public MilitarController(
        IMilitarServices militarServices,
        BusinessResponse businessResponse
    ) {
        this.militarServices = militarServices;
        this.businessResponse = businessResponse;
        this.militarControllerId = UUID.randomUUID().toString();
        log.debug("militarControllerId {}", militarControllerId);
        log.debug("MilitarController loaded successfully");
    }

    @Override
    @GetMapping(path = GET_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> findAll() {

        log.debug("method findAll initialized successfully");
        log.debug("militarControllerId {}", militarControllerId);
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, FIND_ALL_MILITAR_LOG_METHOD);

        return this.militarServices
            .getAll()
            .flatMap(processResponse -> {

                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    return Mono.just(businessResponse
                        .getResponse(processResponse.getResponseCode().getResponseCodeValue()));
                }

                return Mono.just(businessResponse
                    .getResponse(processResponse.getBusinessResponse(),
                        processResponse.getResponseCode().getResponseCodeValue()));
            })
            .doOnSuccess(success ->
                log.info("finish process findAll")
            )
            .onErrorResume(WebExchangeBindException.class, Mono::error)

            .onErrorResume(CommonBusinessProcessException.class, e -> Mono
                .just(
                    businessResponse
                        .getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable ->
                log.error("exception error in process findAll, error: {}", throwable.getMessage())
            );
    }

    @Override
    @GetMapping(path = GET_MILITAR_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> getById(Long militarId) {

        log.debug("method getById initialized successfully");
        log.debug("militarControllerId {}", militarControllerId);
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, FIND_BY_ID_MILITAR_LOG_METHOD);

        return this.militarServices.getById(militarId)
            .flatMap(processResponse -> {
                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    return Mono.just(businessResponse
                        .getResponse(processResponse.getResponseCode().getResponseCodeValue()));
                }
                return Mono.just(businessResponse
                    .getResponse(processResponse.getBusinessResponse(),
                        processResponse.getResponseCode().getResponseCodeValue()));
            }).doOnSuccess(success ->
                log.info("finish process getById")
            )
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono
                .just(
                    businessResponse
                        .getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable ->
                log.error("exception error in process getById, error: {}", throwable.getMessage())
            );
    }

    @Override
    @PostMapping(value = SEARCH_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> getByDni(SearchMilitarRequest search) {

        log.debug("method getByDni initialized successfully");
        log.debug("militarControllerId {}", militarControllerId);
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, FIND_BY_ID_MILITAR_LOG_METHOD);

        return this.militarServices.getByDni(search.getDni())
            .flatMap(processResponse -> {
                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    return Mono.just(businessResponse
                        .getResponse(processResponse.getResponseCode().getResponseCodeValue()));
                }
                return Mono.just(businessResponse
                    .getResponse(processResponse.getBusinessResponse(),
                        processResponse.getResponseCode().getResponseCodeValue()));
            }).doOnSuccess(success ->
                log.info("finish process getByDni")
            )
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono
                .just(
                    businessResponse
                        .getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable ->
                log.error("exception error in process getByDni, error: {}", throwable.getMessage())
            );
    }

    @Override
    @PostMapping(value = SAVE_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> save(Mono<RegisterMilitarRequest> request) {

        log.debug("method save initialized successfully");
        log.debug("militarControllerId {}", militarControllerId);
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, SAVE_MILITAR_LOG_METHOD);

        return this.militarServices.save(request)
            .flatMap(processResponse -> {
                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    return Mono.just(businessResponse
                        .getResponse(processResponse.getResponseCode().getResponseCodeValue()));
                }
                return Mono.just(businessResponse
                    .getResponse(processResponse.getBusinessResponse(),
                        processResponse.getResponseCode().getResponseCodeValue()));
            }).doOnSuccess(success ->
                log.info("finish process save")
            )
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono
                .just(
                    businessResponse
                        .getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable ->
                log.error("exception error in process save, error: {}", throwable.getMessage())
            );
    }

    @Override
    @PutMapping(value = UPDATE_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> update(Mono<RegisterMilitarRequest> request) {
        log.debug("method update initialized successfully");
        log.debug("militarControllerId {}", militarControllerId);
        ThreadContext.put(LoggerConstants.CONSTANTS_LOG_METHOD, UPDATE_MILITAR_LOG_METHOD);

        return this.militarServices.update(request)
            .flatMap(processResponse -> {
                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    return Mono.just(businessResponse
                        .getResponse(processResponse.getResponseCode().getResponseCodeValue()));
                }
                return Mono.just(businessResponse
                    .getResponse(processResponse.getBusinessResponse(),
                        processResponse.getResponseCode().getResponseCodeValue()));
            }).doOnSuccess(success ->
                log.info("finish process update")
            )
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono
                .just(
                    businessResponse
                        .getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable ->
                log.error("exception error in process update, error: {}", throwable.getMessage())
            );
    }

    @Override
    @PostMapping(path = PAGES_MILITAR_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Object>> page(Mono<PageMilitarRequest> request) {
        log.debug("method page initialized successfully");
        log.debug("militarControllerId {}", militarControllerId);

        return this.militarServices.getPage(request)
            .flatMap(processResponse -> {
                if (processResponse.isErrorProcessResponse() || processResponse.isEmptySuccessfullyResponse()) {
                    return Mono.just(businessResponse
                        .getResponse(processResponse.getResponseCode().getResponseCodeValue()));
                }
                return Mono.just(businessResponse
                    .getResponse(processResponse.getBusinessResponse(),
                        processResponse.getResponseCode().getResponseCodeValue()));
            }).doOnSuccess(success ->
                log.info("finish process update")
            )
            .onErrorResume(WebExchangeBindException.class, Mono::error)
            .onErrorResume(CommonBusinessProcessException.class, e -> Mono
                .just(
                    businessResponse
                        .getResponse(e.getResponseCode().getResponseCodeValue())))
            .doOnError(throwable ->
                log.error("exception error in process page, error: {}", throwable.getMessage())
            );
    }
}
