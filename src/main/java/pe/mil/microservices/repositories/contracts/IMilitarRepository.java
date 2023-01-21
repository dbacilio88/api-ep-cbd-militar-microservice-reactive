package pe.mil.microservices.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mil.microservices.repositories.entities.MilitarEntity;

import java.util.Optional;

@Repository
public interface IMilitarRepository extends JpaRepository<MilitarEntity, Long> {
    boolean existsByMilitarId(Long customerId);

    @Query(value = "SELECT m FROM MilitarEntity m WHERE m.person.dni = :dni")
    Optional<MilitarEntity> findByDni(@Param("dni") String dni);
}
