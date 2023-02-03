package pe.mil.microservices.repositories.contracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mil.microservices.repositories.entities.MilitarEntity;

import java.util.Optional;

@Repository
public interface IMilitarRepository extends JpaRepository<MilitarEntity, String> {
    @Query("SELECT CASE WHEN count(m) > 0 THEN true ELSE false END FROM MilitarEntity m where  m.militarId = :militarId AND m.person.personId = :personId")
    boolean existsByMilitarIdAndPerson(@Param("militarId") String militarId, @Param("personId") String personId);

    @Query(value = "SELECT m FROM MilitarEntity m WHERE m.person.personId = :personId")
    Optional<MilitarEntity> findByDni(@Param("personId") String personId);

}
