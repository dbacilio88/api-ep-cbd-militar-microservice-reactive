package pe.mil.microservices.repositories.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static pe.mil.microservices.constants.RepositoryEntitiesConstants.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = ENTITY_MILITAR)
@NoArgsConstructor
@AllArgsConstructor
public class MilitarEntity {

    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 9, max = 9)
    @Column(name = ENTITY_MILITAR_ID, length = 9, unique = true, updatable = false)
    private String militarId;

    @NotNull
    @OneToOne
    @JoinColumn(name = ENTITY_PERSON_ID, updatable = false, nullable = false, unique = true)
    private PersonEntity person;

    @NotNull
    @OneToOne
    @JoinColumn(name = ENTITY_GRADE_ID, nullable = false)
    private GradesEntity grade;

    @NotNull
    @OneToOne
    @JoinColumn(name = ENTITY_SPECIALTY_ID, nullable = false)
    private SpecialtyEntity specialty;
}
