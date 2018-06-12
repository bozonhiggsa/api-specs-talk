package io.github.q1nt.todoapp.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class ErrorResponse {

    @JsonProperty("errors")
    private Collection<Error> errors;

}
