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
@Table(name = ENTITY_GRADE)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class GradesEntity {

    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = ENTITY_GRADES_ID)
    private Long gradeId;

    @NotEmpty
    @Column(name = ENTITY_GRADES_NAME, unique = true)
    private String name;

    @NotEmpty
    @Column(name = ENTITY_GRADES_DESCRIPTION)
    private String description;
}
