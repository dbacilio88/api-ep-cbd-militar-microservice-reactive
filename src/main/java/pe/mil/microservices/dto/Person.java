package pe.mil.microservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.utils.components.validations.Dni;

import javax.validation.constraints.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Person implements Serializable {

    private static final long serialVersionUID = -6951550954051995262L;

    @NotBlank
    @Dni
    @Size(max = 8, min = 8)
    private String personId;

    private String name;

    private String lastName;

}
