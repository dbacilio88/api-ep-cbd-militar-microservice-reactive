package pe.mil.microservices.services.implementations;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pe.mil.microservices.components.enums.MilitarValidationResult;
import pe.mil.microservices.components.enums.PageValidationResult;
import pe.mil.microservices.components.mappers.contracts.IMilitarMapperByMapstruct;
import pe.mil.microservices.components.validations.IMilitarRegisterValidation;
import pe.mil.microservices.components.validations.IPageMilitarValidation;
import pe.mil.microservices.dto.Militar;
import pe.mil.microservices.dto.requests.PageMilitarRequest;
import pe.mil.microservices.dto.requests.RegisterMilitarRequest;
import pe.mil.microservices.dto.responses.RegisterMilitarResponse;
import pe.mil.microservices.repositories.contracts.IMilitarRepository;
import pe.mil.microservices.repositories.entities.MilitarEntity;
import pe.mil.microservices.services.base.BaseReactorService;
import pe.mil.microservices.services.contracts.IMilitarServices;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.components.exceptions.CommonBusinessProcessException;
import pe.mil.microservices.utils.components.helpers.ObjectMapperHelper;
import pe.mil.microservices.utils.dtos.generics.GenericBusinessResponse;
import pe.mil.microservices.utils.dtos.generics.PageableGenericResponse;
import pe.mil.microservices.utils.dtos.process.BusinessProcessResponse;
import pe.mil.microservices.utils.dtos.responses.MetadataResponse;
import pe.mil.microservices.utils.dtos.responses.PageableResponse;
import reactor.core.publisher.Mono;

import java.util.*;

import static pe.mil.microservices.constants.ProcessConstants.*;
import static pe.mil.microservices.utils.constants.LoggerConstants.*;

@Log4j2
@Service
public class MilitarService extends BaseReactorService implements IMilitarServices {

    private final IMilitarRepository militarRepository;

    private MilitarService(
        final IMilitarRepository militarRepository) {
        super(MilitarService.log.getName());
        this.militarRepository = militarRepository;
    }

    @Override
    public Mono<BusinessProcessResponse> getById(String id) {
        log.info(MICROSERVICE_THIS_SERVICES_METHOD_FORMAT, FIND_BY_ID_METHOD_NAME);
        log.debug(MICROSERVICE_SERVICE_ID_FORMAT, this.getMilitarServiceId());
        GenericBusinessResponse<Militar> genericMessagesBusinessResponse = new GenericBusinessResponse<>();

        return Mono
            .just(genericMessagesBusinessResponse)
            .flatMap(generic -> {
                final Optional<MilitarEntity> entity = this.militarRepository.findById(id);
                if (entity.isEmpty()) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.SUCCESS_IN_REQUESTED_DATA_NOT_FOUND));
                } else {
                    return Mono.just(entity.get());
                }
            })
            .flatMap(entity -> Mono.just(searchMilitar(entity)))
            .flatMap(response -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(response)))
            .doOnSuccess(success -> log.info(MICROSERVICE_FINISH_PROCESS_SERVICE_FORMAT, FIND_BY_ID_METHOD_NAME, success))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, FIND_BY_ID_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    public Mono<BusinessProcessResponse> getAll() {
        log.info(MICROSERVICE_THIS_SERVICES_METHOD_FORMAT, FIND_ALL_METHOD_NAME);
        log.debug(MICROSERVICE_SERVICE_ID_FORMAT, this.getMilitarServiceId());
        GenericBusinessResponse<List<Militar>> genericMessagesBusinessResponse = new GenericBusinessResponse<>();
        return Mono.just(genericMessagesBusinessResponse)
            .flatMap(generic -> {
                generic.setData(ObjectMapperHelper.mapAll(Lists.newArrayList(this.militarRepository.findAll()), Militar.class));
                return Mono.just(generic);
            })
            .flatMap(response -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(response)))
            .flatMap(process -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(process.getBusinessResponse())))
            .doOnSuccess(success -> log.info(MICROSERVICE_FINISH_PROCESS_SERVICE_FORMAT, FIND_ALL_METHOD_NAME, success.toString()))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, FIND_ALL_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    public Iterable<Mono<BusinessProcessResponse>> getAllEntities() {
        return new ArrayList<>();
    }

    @Override
    public Mono<BusinessProcessResponse> save(Mono<RegisterMilitarRequest> entity) {
        log.info(MICROSERVICE_THIS_SERVICES_METHOD_FORMAT, SAVE_METHOD_NAME);
        log.debug(MICROSERVICE_SERVICE_ID_FORMAT, this.getMilitarServiceId());
        return entity
            .flatMap(create -> {
                final MilitarValidationResult result = validationRequest(create);

                if (!MilitarValidationResult.MILITAR_VALID.equals(result)) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(create);
            })
            .flatMap(request -> {
                boolean exists = this.militarRepository.existsByMilitarIdAndPerson(request.getMilitarId(), request.getPerson().getPersonId());
                if (Boolean.TRUE.equals(exists)) {

                    return Mono.error(() -> new CommonBusinessProcessException(
                        ResponseCode.ERROR_IN_REQUESTED_DATA_EXISTS.getResponseCodeValue(), ResponseCode.ERROR_IN_REQUESTED_DATA_EXISTS)
                    );
                }
                final MilitarEntity saved = saveMilitar(request);
                if (Objects.isNull(saved.getMilitarId())) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_DATA_REGISTER_IN_DATABASE.getResponseCodeValue(), ResponseCode.ERROR_DATA_REGISTER_IN_DATABASE));
                }
                return Mono.just(saved);
            })
            .flatMap(response -> Mono.just(militarResponse(response)))
            .doOnSuccess(success -> log.info(MICROSERVICE_FINISH_PROCESS_SERVICE_FORMAT, SAVE_METHOD_NAME, success.toString()))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, SAVE_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    public Boolean delete(Militar entity) {
        log.debug(MICROSERVICE_THIS_SERVICES_METHOD_FORMAT, DELETE_METHOD_NAME);
        log.debug(MICROSERVICE_SERVICE_ID_FORMAT, this.getMilitarServiceId());
        final Optional<MilitarEntity> result = this.militarRepository.findById(entity.getMilitarId());
        if (result.isEmpty()) {
            return false;
        }
        this.militarRepository.delete(result.get());
        return true;
    }

    @Override
    public Mono<BusinessProcessResponse> getPage(Mono<PageMilitarRequest> entity) {
        return entity
            .flatMap(request -> {
                final PageValidationResult result = IPageMilitarValidation.isPageMilitarLimitValidation()
                    .and(IPageMilitarValidation.isPageMilitarPageValidation())
                    .apply(request);
                if (!PageValidationResult.PAGE_VALID.equals(result)) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(request);
            })
            .flatMap(request -> {
                int limit = Integer.parseInt(request.getLimit());
                int page = Integer.parseInt(request.getPage());
                Pageable pageable = PageRequest.of(page - 1, limit);
                Page<MilitarEntity> militares = this.militarRepository.findAll(pageable);
                PageableGenericResponse<List<Militar>> response = new PageableGenericResponse<>();
                response.setData(ObjectMapperHelper.mapAll(Lists.newArrayList(militares.getContent()), Militar.class));
                /***
                 * @apiNote properties additional pagination:
                BigInteger totalRecords = longToBigInteger(militares.getTotalElements());
                BigDecimal lastPage = getLastPage(totalRecords, request.getLimit());
                BigInteger nextPageNumber = getNextPageNumber(request.getPage());
                BigInteger previousPageNumber = previousPageNumber(request.getPage());
                String nextPageNumberAsString = getNextPageNumber(nextPageNumber, lastPage.toBigInteger(), request.getLimit());
                String previousPageNumberAsString = getPreviousPageNumber(previousPageNumber, lastPage.toBigInteger(), request.getLimit());
                String lastPageAsString = getLastPage(lastPage, request.getLimit());
                String firstPageAsString = getFirstPage(totalRecords, request.getLimit());
                 */
                PageableResponse pageableResponse = PageableResponse
                    .builder()
                    .total(String.valueOf(militares.getTotalElements()))
                    .limit(request.getLimit())
                    .currentPage(request.getPage())
                    .numberOfElements(String.valueOf(militares.getNumberOfElements()))
                    .totalPages(String.valueOf(militares.getTotalPages()))
                    /**
                     .firstPageUrl(firstPageAsString)
                     .lastPageUrl(lastPageAsString)
                     .nextPageUrl(nextPageNumberAsString)
                     .prevPageUrl(previousPageNumberAsString)
                     .lastPage(String.valueOf(lastPage))
                     */
                    .build();
                response.setMetadata(MetadataResponse.builder()
                    .pageable(pageableResponse).build());
                return Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(response));
            })
            .doOnSuccess(success -> log.info(MICROSERVICE_FINISH_PROCESS_SERVICE_FORMAT, PAGE_METHOD_NAME, success))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, PAGE_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    public Mono<BusinessProcessResponse> update(Mono<RegisterMilitarRequest> entity) {
        log.info(MICROSERVICE_THIS_SERVICES_METHOD_FORMAT, UPDATE_METHOD_NAME);
        log.debug(MICROSERVICE_SERVICE_ID_FORMAT, this.getMilitarServiceId());
        return entity.
            flatMap(request -> {
                final MilitarValidationResult result = validationRequest(request);
                if (!MilitarValidationResult.MILITAR_VALID.equals(result)) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(request);
            })
            .flatMap(request -> {
                final MilitarEntity updated = saveMilitar(request);
                if (Objects.isNull(updated.getMilitarId())) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(updated);
            })
            .flatMap(response -> Mono.just(militarResponse(response)))
            .doOnSuccess(success -> log.info(MICROSERVICE_FINISH_PROCESS_SERVICE_FORMAT, UPDATE_METHOD_NAME, success))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, UPDATE_METHOD_NAME, throwable.getMessage()));
    }

    @Override
    public Mono<BusinessProcessResponse> getByDni(String dni) {
        log.info(MICROSERVICE_THIS_SERVICES_METHOD_FORMAT, FIND_BY_DNI_METHOD_NAME);
        log.debug(MICROSERVICE_SERVICE_ID_FORMAT, this.getMilitarServiceId());
        GenericBusinessResponse<Militar> genericMessagesBusinessResponse = new GenericBusinessResponse<>();
        return Mono.just(genericMessagesBusinessResponse)
            .flatMap(generic -> {
                final Optional<MilitarEntity> entity = this.militarRepository.findByDni(dni);
                if (entity.isEmpty()) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.SUCCESS_IN_REQUESTED_DATA_NOT_FOUND));
                } else {
                    return Mono.just(entity.get());
                }
            })
            .flatMap(entity -> Mono.just(searchMilitar(entity)))
            .flatMap(response -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(response)))
            .doOnSuccess(success -> log.info(MICROSERVICE_FINISH_PROCESS_SERVICE_FORMAT, FIND_BY_DNI_METHOD_NAME, success))
            .doOnError(throwable -> log.error(MICROSERVICE_PROCESS_ERROR_SERVICE_FORMAT, FIND_BY_DNI_METHOD_NAME, throwable.getMessage()));
    }


    private GenericBusinessResponse<Militar> searchMilitar(MilitarEntity entity) {
        final Militar target = ObjectMapperHelper.map(entity, Militar.class);
        return new GenericBusinessResponse<>(target);
    }

    private MilitarEntity saveMilitar(RegisterMilitarRequest request) {
        final MilitarEntity update = IMilitarMapperByMapstruct
            .MILITAR_MAPPER
            .mapMilitarEntityByRegisterMilitarRequest(request);
        return this.militarRepository.save(update);
    }

    private MilitarValidationResult validationRequest(RegisterMilitarRequest request) {

        return IMilitarRegisterValidation
            .isMilitarIdValidation()
            .and(IMilitarRegisterValidation.isMilitarPersonValidation())
            .and(IMilitarRegisterValidation.isMilitarGradeValidation())
            .and(IMilitarRegisterValidation.isMilitarSpecialtyValidation())
            .apply(request);
    }

    private BusinessProcessResponse militarResponse(MilitarEntity entity) {
        final RegisterMilitarResponse response = ObjectMapperHelper
            .map(entity, RegisterMilitarResponse.class);
        return BusinessProcessResponse
            .setEntitySuccessfullyResponse(new GenericBusinessResponse<>(response));
    }
}