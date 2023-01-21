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
@Table(name = ENTITY_PERSON)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonEntity {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = ENTITY_PERSON_ID)
    private Long personId;

    @NotEmpty
    @Column(name = ENTITY_PERSON_NAME)
    private String name;

    @NotEmpty
    @Column(name = ENTITY_PERSON_LAST_NAME)
    private String lastName;

    @NotEmpty
    @Column(name = ENTITY_PERSON_DOCUMENT_NUMBER, length = 8, unique = true)
    private String dni;
}
