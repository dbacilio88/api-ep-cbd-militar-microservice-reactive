package pe.mil.microservices.dto.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageMilitarRequest implements Serializable {

    private static final long serialVersionUID = 5230901019196076398L;

    @NotBlank
    @Size(min = 1)
    private String limit;

    @NotBlank
    @Size(min = 1)
    private String page;

}
