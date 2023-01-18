package pe.mil.microservices.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Grade implements Serializable {

    private static final long serialVersionUID = -6951550954051995262L;

    @NotNull
    @Min(1)
    private long gradeId;
    private String name;
    private String description;
}
