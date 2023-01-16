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
public class Person implements Serializable {

    private static final long serialVersionUID = -6951550954051995262L;

    private long personId;
    private String name;
    private String lastName;
    private String dni;
}
