package pe.mil.microservices.services.contracts;

import pe.mil.microservices.dto.Militar;
import pe.mil.microservices.dto.requests.PageMilitarRequest;
import pe.mil.microservices.dto.requests.RegisterMilitarRequest;
import pe.mil.microservices.services.interfaces.IGetDomainEntityByDni;
import pe.mil.microservices.utils.dtos.process.BusinessProcessResponse;
import pe.mil.microservices.utils.service.interfaces.*;
import reactor.core.publisher.Mono;

public interface IMilitarServices
    extends
    IGetDomainEntityById<Mono<BusinessProcessResponse>, Long>,
    IGetAllDomainEntity<Mono<BusinessProcessResponse>>,
    ISaveDomainEntity<Mono<BusinessProcessResponse>, Mono<RegisterMilitarRequest>>,
    IUpdateDomainEntity<Mono<BusinessProcessResponse>, Mono<RegisterMilitarRequest>>,
    IDeleteDomainEntity<Militar>,
    IGetDomainEntityByDni<Mono<BusinessProcessResponse>, String>,
    IGetDomainEntityPage<Mono<BusinessProcessResponse>, Mono<PageMilitarRequest>> {
}
