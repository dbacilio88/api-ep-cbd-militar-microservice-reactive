package pe.mil.microservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.utils.components.validations.Dni;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Militar implements Serializable {

    private static final long serialVersionUID = -6951550954051995262L;

    @NotBlank
    @Dni
    @Size(max = 9, min = 9)
    private String militarId;
    private Person person;
    private Grade grade;
    private Specialty specialty;

}
