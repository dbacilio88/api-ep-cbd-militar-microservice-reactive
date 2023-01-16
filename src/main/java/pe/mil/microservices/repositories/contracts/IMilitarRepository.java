package pe.mil.microservices.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.mil.microservices.repositories.entities.MilitarEntity;

@Repository
public interface IMilitarRepository extends JpaRepository<MilitarEntity, Long> {
    boolean existsByMilitarId(Long customerId);


}
