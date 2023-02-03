package pe.mil.microservices.repositories.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import static pe.mil.microservices.constants.RepositoryEntitiesConstants.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = ENTITY_SPECIALTY)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SpecialtyEntity {

    @Id
    @Basic(optional = false)
    @NotEmpty
    @Size(min = 3, max = 3)
    @Column(name = ENTITY_SPECIALTY_ID, length = 3, unique = true, updatable = false)
    private String specialtyId;

    @NotEmpty
    @Column(name = ENTITY_SPECIALTY_NAME, unique = true)
    private String name;

    @NotEmpty
    @Column(name = ENTITY_SPECIALTY_DESCRIPTION)
    private String description;
}
