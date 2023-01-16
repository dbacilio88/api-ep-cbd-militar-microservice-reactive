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
public class Specialty implements Serializable {

    private static final long serialVersionUID = -6951550954051995262L;

    private long specialtyId;
    private String name;
    private String description;
}
