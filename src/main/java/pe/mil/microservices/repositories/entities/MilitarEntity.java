package pe.mil.microservices.repositories.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
    @Column(name = ENTITY_MILITAR_ID)
    private Long militarId;

    @NotEmpty
    @Column(name = ENTITY_MILITAR_CIP, length = 9, nullable = false)
    private String cip;

    @Column(name = ENTITY_MILITAR_DNI, length = 8)
    private String dni;

    @NotNull
    @OneToOne
    @JoinColumn(name = ENTITY_PERSON_ID, updatable = false, nullable = false)
    private PersonEntity person;

    @NotNull
    @OneToOne
    @JoinColumn(name = ENTITY_GRADE_ID, updatable = false, nullable = false)
    private GradesEntity grade;

    @NotNull
    @OneToOne
    @JoinColumn(name = ENTITY_SPECIALTY_ID, updatable = false, nullable = false)
    private SpecialtyEntity specialty;
}
