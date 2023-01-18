package pe.mil.microservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

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

    @NotNull
    @Min(1)
    private long personId;

    private String name;

    private String lastName;

    @NotBlank
    //@Pattern(regexp = REGEX_DOCUMENT_NUMBER, message = REGEX_DOCUMENT_NUMBER_MESSAGE)
    @Size(max = 8, min = 8)
    private String dni;
}
