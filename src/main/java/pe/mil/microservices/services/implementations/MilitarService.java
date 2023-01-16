package pe.mil.microservices.services.implementations;

import com.google.common.collect.Lists;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.mil.microservices.components.enums.MilitarValidationResult;
import pe.mil.microservices.components.mappers.contracts.IMilitarMapperByMapstruct;
import pe.mil.microservices.components.validations.IMilitarRegisterValidation;
import pe.mil.microservices.dto.Militar;
import pe.mil.microservices.dto.requests.RegisterMilitarRequest;
import pe.mil.microservices.dto.responses.RegisterMilitarResponse;
import pe.mil.microservices.repositories.contracts.IMilitarRepository;
import pe.mil.microservices.repositories.entities.MilitarEntity;
import pe.mil.microservices.services.contracts.IMilitarServices;
import pe.mil.microservices.utils.components.enums.ResponseCode;
import pe.mil.microservices.utils.components.exceptions.CommonBusinessProcessException;
import pe.mil.microservices.utils.components.helpers.ObjectMapperHelper;
import pe.mil.microservices.utils.dtos.base.GenericBusinessResponse;
import pe.mil.microservices.utils.dtos.process.BusinessProcessResponse;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
public class MilitarService implements IMilitarServices {

    private final IMilitarRepository militarRepository;
    private final String militarServiceId;


    @Autowired
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
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                } else {
                    return Mono.just(entity.get());
                }
            })
            .flatMap(entity -> {
                final Militar target = ObjectMapperHelper.map(entity, Militar.class);
                log.info("target {} ", target.toString());
                GenericBusinessResponse<Militar> data = new GenericBusinessResponse<>(target);
                return Mono.just(data);
            })
            .flatMap(response -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(response)))
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
            .flatMap(response -> {
                log.info("response {} ", response.getData().toString());
                return Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(response));
            }).flatMap(process -> Mono.just(BusinessProcessResponse.setEntitySuccessfullyResponse(process.getBusinessResponse())))
            .doOnSuccess(success ->
                log.info("finish process getById, success: {}", success.toString())
            )
            .doOnError(throwable ->
                log.error("exception error in process getById, error: {}", throwable.getMessage())
            );
    }

    @Override
    public Mono<BusinessProcessResponse> save(Mono<RegisterMilitarRequest> entity) {

        log.info("this is in services save method");
        log.debug("militarServiceId {}", militarServiceId);

        return entity
            .flatMap(create -> {
                log.debug("this is in services save demo method");

                final MilitarValidationResult result =
                    IMilitarRegisterValidation
                        .isMilitarIdValidation()
                        .and(IMilitarRegisterValidation.isMilitarCipValidation())
                        .and(IMilitarRegisterValidation.isMilitarPersonValidation())
                        .and(IMilitarRegisterValidation.isMilitarGradeValidation())
                        .and(IMilitarRegisterValidation.isMilitarSpecialtyValidation())
                        .apply(create);
                log.info("result {} ", result);
                if (!MilitarValidationResult.MILITAR_VALID.equals(result)) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(create);
            })
            .flatMap(request -> {

                log.info("log in flatMap context request {}", request);
                final MilitarEntity save =
                    IMilitarMapperByMapstruct.MILITAR_MAPPER.mapMilitarEntityByRegisterMilitarRequest(request);
                log.info("save entity {} ", save);

                boolean exists = this.militarRepository.existsByMilitarId(save.getMilitarId());
                log.info("exists entity {} ", exists);

                if (exists) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA_EXISTS));
                }

                final MilitarEntity saved = this.militarRepository.save(save);
                log.info("saved entity {} ", saved);

                if (Objects.isNull(saved.getMilitarId())) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }

                return Mono.just(saved);
            })
            .flatMap(militar -> {
                log.info("militar entity {} ", militar);
                final RegisterMilitarResponse response = ObjectMapperHelper
                    .map(militar, RegisterMilitarResponse.class);
                return Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(new GenericBusinessResponse<>(response)));
            })
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
    public Mono<BusinessProcessResponse> update(Mono<RegisterMilitarRequest> entity) {

        log.info("this is in services update method");
        log.debug("militarServiceId {}", militarServiceId);

        return entity.
            flatMap(update -> {
                log.debug("this is in services update method");
                final MilitarValidationResult result = IMilitarRegisterValidation
                    .isMilitarCipValidation().apply(update);
                if (!MilitarValidationResult.MILITAR_VALID.equals(result)) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(update);
            })
            .flatMap(request -> {
                log.debug("log in flatMap context #2");
                final MilitarEntity update = IMilitarMapperByMapstruct
                    .MILITAR_MAPPER
                    .mapMilitarEntityByRegisterMilitarRequest(request);

                final MilitarEntity updated = this.militarRepository.save(update);

                if (Objects.isNull(updated.getMilitarId())) {
                    return Mono.error(() -> new CommonBusinessProcessException(ResponseCode.ERROR_IN_REQUESTED_DATA));
                }
                return Mono.just(updated);

            })
            .flatMap(customer -> {
                final RegisterMilitarResponse response = ObjectMapperHelper
                    .map(customer, RegisterMilitarResponse.class);
                return Mono.just(BusinessProcessResponse
                    .setEntitySuccessfullyResponse(new GenericBusinessResponse<>(response)));
            }).doOnSuccess(success ->
                log.info("finish process save, success: {}", success)
            )
            .doOnError(throwable ->
                log.error("exception error in process save, error: {}", throwable.getMessage())
            );
    }
}
