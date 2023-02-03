package pe.mil.microservices.repositories.entities;

import lombok.*;
import pe.mil.microservices.utils.components.validations.Dni;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static pe.mil.microservices.constants.RepositoryEntitiesConstants.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = ENTITY_PERSON)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonEntity {

    @Id
    @Basic(optional = false)
    @NotNull
    @Dni
    @Size(min = 8, max = 8)
    @Column(name = ENTITY_PERSON_ID, updatable = false, unique = true, length = 8)
    private String personId;

    @NotEmpty
    @Column(name = ENTITY_PERSON_NAME)
    private String names;

    @NotEmpty
    @Column(name = ENTITY_PERSON_LAST_NAME)
    private String lastName;
}
