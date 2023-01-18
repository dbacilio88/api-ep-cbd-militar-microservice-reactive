package pe.mil.microservices.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.mil.microservices.repositories.entities.MilitarEntity;

import java.util.Optional;

@Repository
public interface IMilitarRepository extends JpaRepository<MilitarEntity, Long> {
    boolean existsByMilitarId(Long customerId);

    Optional<MilitarEntity> findByDni(String dni);
}
