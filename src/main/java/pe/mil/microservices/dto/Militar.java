package pe.mil.microservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Militar implements Serializable {

    private static final long serialVersionUID = -6951550954051995262L;

    private long militarId;
    private String cip;
    private Person person;
    private Grade grade;
    private Specialty specialty;

}
