package pe.mil.microservices.services.interfaces;

public interface IGetDomainEntityByDni<E, I> {
    E getByDni(I id);
}
