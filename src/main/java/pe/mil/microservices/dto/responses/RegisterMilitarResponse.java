package pe.mil.microservices.dto.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.dto.Grade;
import pe.mil.microservices.dto.Person;
import pe.mil.microservices.dto.Specialty;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegisterMilitarResponse implements Serializable {

    private static final long serialVersionUID = 5230901019196076399L;
    private long militarId;
    private String cip;
    private Person person;
    private Grade grade;
    private Specialty specialty;
}
