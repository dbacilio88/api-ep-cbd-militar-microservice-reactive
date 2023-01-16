package pe.mil.microservices.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.dto.Grade;
import pe.mil.microservices.dto.Person;
import pe.mil.microservices.dto.Specialty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterMilitarRequest implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;

    @NotNull
    private Long militarId;

    @NotBlank
    @Size(max = 9, min = 9)
    private String cip;

    @NotNull
    private Person person;

    @NotNull
    private Grade grade;

    @NotNull
    private Specialty specialty;
}
