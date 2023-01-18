package pe.mil.microservices.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import pe.mil.microservices.components.validations.DocumentNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SearchMilitarRequest implements Serializable {

    private static final long serialVersionUID = 5230901019196076398L;

    @NotBlank
    @DocumentNumber
    @Size(max = 8, min = 8)
    private String dni;

}
