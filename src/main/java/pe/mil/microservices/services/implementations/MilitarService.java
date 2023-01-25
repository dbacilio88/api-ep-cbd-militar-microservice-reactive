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
import pe.mil.microservices.services.contracts.IMilitarServices;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.components.exceptions.CommonBusinessProcessException;
import pe.mil.microservices.utils.components.helpers.CommonPageHelper;
import pe.mil.microservices.utils.components.helpers.ObjectMapperHelper;
import pe.mil.microservices.utils.dtos.base.GenericBusinessResponse;
import pe.mil.microservices.utils.dtos.base.PageableBusinessResponse;
import pe.mil.microservices.utils.dtos.process.BusinessProcessResponse;
import pe.mil.microservices.utils.dtos.responses.MetadataResponse;
import pe.mil.microservices.utils.dtos.responses.PageableResponse;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static pe.mil.microservices.utils.components.helpers.CommonPageHelper.*;

@Log4j2
@Service
public class MilitarService implements IMilitarServices {

    private final IMilitarRepository militarRepository;
    private final String militarServiceId;


    public MilitarService(
        final IMilitarRepository militarRepository
    ) {
        this.militarRepository = militarRepository;
        militarServiceId = UUID.randomUUID().toString();
        log.debug("militarServiceId {}", militarServiceId);
        log.debug("CustomerService loaded successfully");
    }

    @Override
    public Mono<BusinessProcessResponse> getById(Long id) {

        log.info("this is in services getById method");
        log.debug("militarServiceId {}", militarServiceId);

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
            .flatMap(entity ->
                Mono.just(searchMilitar(entity)
                )
            )
            .flatMap(response ->
                Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(response)
                )
            )
            .doOnSuccess(success ->
                log.info("finish process getById, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process getById, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Mono<BusinessProcessResponse> getAll() {
        log.info("this is in services getAll method");
        log.debug("militarServiceId {}", militarServiceId);
        GenericBusinessResponse<List<Militar>> genericMessagesBusinessResponse = new GenericBusinessResponse<>();

        return Mono.just(genericMessagesBusinessResponse)
            .flatMap(generic -> {
                generic.setData(ObjectMapperHelper.mapAll(Lists.newArrayList(this.militarRepository.findAll()), Militar.class));
                return Mono.just(generic);
            })
            .flatMap(response ->
                Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(response)))
            .flatMap(process ->
                Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(process.getBusinessResponse())
                ))
            .doOnSuccess(success ->
                log.info("finish process getById, success: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("exception error in process getById, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Iterable<Mono<BusinessProcessResponse>> GetAllEntities() {
        return null;
    }

    @Override
    public Mono<BusinessProcessResponse> save(Mono<RegisterMilitarRequest> entity) {

        log.info("this is in services save method");
        log.debug("militarServiceId {}", militarServiceId);

        return entity
            .flatMap(create -> {

                final MilitarValidationResult result = validationRequest(create);

                if (!MilitarValidationResult.MILITAR_VALID.equals(result)) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }

                return Mono.just(create);
            })
            .flatMap(request -> {

                boolean exists = this.militarRepository.existsByMilitarId(request.getMilitarId());

                if (exists) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA_EXISTS));
                }

                final MilitarEntity saved = saveMilitar(request);

                if (Objects.isNull(saved.getMilitarId())) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }

                return Mono.just(saved);
            })
            .flatMap(response ->
                Mono.just(militarResponse(response)
                )
            )
            .doOnSuccess(success ->
                log.info("finish process save, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process save, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Boolean delete(Militar entity) {

        log.debug("this is in services delete method");
        log.debug("militarServiceId {}", militarServiceId);

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

                PageableBusinessResponse<List<Militar>> response = new PageableBusinessResponse<>();

                response.setData(ObjectMapperHelper.mapAll(Lists.newArrayList(militares.getContent()), Militar.class));

                BigInteger totalRecords = longToBigInteger(militares.getTotalElements());
                BigDecimal lastPage = getLastPage(totalRecords, request.getLimit());
                BigInteger nextPageNumber = getNextPageNumber(request.getPage());
                BigInteger previousPageNumber = previousPageNumber(request.getPage());
                String nextPageNumberAsString = getNextPageNumber(nextPageNumber, lastPage.toBigInteger(), request.getLimit());
                String previousPageNumberAsString = getPreviousPageNumber(previousPageNumber, lastPage.toBigInteger(), request.getLimit());
                String lastPageAsString = getLastPage(lastPage, request.getLimit());
                String firstPageAsString = getFirstPage(totalRecords, request.getLimit());

                PageableResponse pageableResponse = PageableResponse
                    .builder()
                    .total(String.valueOf(militares.getTotalElements()))
                    .limit(request.getLimit())
                    .currentPage(request.getPage())
                    .numberOfElements(String.valueOf(militares.getNumberOfElements()))
                    .totalPages(String.valueOf(militares.getTotalPages()))
                    //.firstPageUrl(firstPageAsString)
                    //.lastPageUrl(lastPageAsString)
                    //.nextPageUrl(nextPageNumberAsString)
                    //.prevPageUrl(previousPageNumberAsString)
                    //.lastPage(String.valueOf(lastPage))
                    .build();

                response.setMetadata(MetadataResponse.builder()
                    .pageable(pageableResponse).build());

                return Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(response));
            })
            .doOnSuccess(success ->
                log.info("finish process page, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process page, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Mono<BusinessProcessResponse> update(Mono<RegisterMilitarRequest> entity) {

        log.info("this is in services update method");
        log.debug("militarServiceId {}", militarServiceId);

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
            .flatMap(response ->
                Mono.just(militarResponse(response)
                )
            )
            .doOnSuccess(success ->
                log.info("finish process save, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process save, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Mono<BusinessProcessResponse> getByDni(String dni) {

        log.info("this is in services getByDni method");
        log.debug("militarServiceId {}", militarServiceId);

        GenericBusinessResponse<Militar> genericMessagesBusinessResponse = new GenericBusinessResponse<>();

        return Mono
            .just(genericMessagesBusinessResponse)
            .flatMap(generic -> {
                final Optional<MilitarEntity> entity = this.militarRepository.findByDni(dni);

                if (entity.isEmpty()) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.SUCCESS_IN_REQUESTED_DATA_NOT_FOUND));
                } else {
                    return Mono.just(entity.get());
                }
            })
            .flatMap(entity -> Mono.just(searchMilitar(entity)))
            .flatMap(response ->
                Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(response)
                )
            )
            .doOnSuccess(success ->
                log.info("finish process getByDni, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process getByDni, error: {}", throwable.getMessage())
            );
    }


    private GenericBusinessResponse<Militar> searchMilitar(MilitarEntity entity) {
        final Militar target = ObjectMapperHelper.map(entity, Militar.class);
        GenericBusinessResponse<Militar> data = new GenericBusinessResponse<>(target);
        return data;
    }

    private MilitarEntity saveMilitar(RegisterMilitarRequest request) {
        log.info("operation save or update militar");
        final MilitarEntity update = IMilitarMapperByMapstruct
            .MILITAR_MAPPER
            .mapMilitarEntityByRegisterMilitarRequest(request);
        final MilitarEntity updated = this.militarRepository.save(update);
        return updated;
    }

    private MilitarValidationResult validationRequest(RegisterMilitarRequest request) {
        final MilitarValidationResult result =
            IMilitarRegisterValidation
                .isMilitarIdValidation()
                .and(IMilitarRegisterValidation.isMilitarCipValidation())
                .and(IMilitarRegisterValidation.isMilitarPersonValidation())
                .and(IMilitarRegisterValidation.isMilitarGradeValidation())
                .and(IMilitarRegisterValidation.isMilitarSpecialtyValidation())
                .apply(request);

        return result;
    }

    private BusinessProcessResponse militarResponse(MilitarEntity entity) {
        final RegisterMilitarResponse response = ObjectMapperHelper
            .map(entity, RegisterMilitarResponse.class);

        return BusinessProcessResponse
            .setEntitySuccessfullyResponse(new GenericBusinessResponse<>(response));
    }


}
