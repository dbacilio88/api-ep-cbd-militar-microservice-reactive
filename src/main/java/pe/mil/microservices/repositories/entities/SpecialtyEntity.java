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
@Table(name = ENTITY_SPECIALTY)
@NoArgsConstructor
@AllArgsConstructor
public class SpecialtyEntity {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = ENTITY_SPECIALTY_ID)
    private Long specialtyId;

    @NotEmpty
    @Column(name = ENTITY_SPECIALTY_NAME, unique = true)
    private String name;

    @NotEmpty
    @Column(name = ENTITY_SPECIALTY_DESCRIPTION)
    private String description;
}
