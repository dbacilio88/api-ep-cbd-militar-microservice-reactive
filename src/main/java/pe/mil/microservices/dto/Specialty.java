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
public class Specialty implements Serializable {

    private static final long serialVersionUID = -6951550954051995262L;

    @NotBlank
    @Dni
    @Size(max = 3, min = 3)
    private String specialtyId;
    private String name;
    private String description;
}
