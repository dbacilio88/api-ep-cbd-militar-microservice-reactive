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
    @Column(name = ENTITY_MILITAR_CIP, unique = true, length = 9)
    private String cip;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ENTITY_PERSON_ID, unique = true, nullable = false)
    private PersonEntity person;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ENTITY_GRADE_ID, nullable = false)
    private GradesEntity grade;

    @NotNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = ENTITY_SPECIALTY_ID, nullable = false)
    private SpecialtyEntity specialty;
}
