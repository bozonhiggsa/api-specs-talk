package io.github.q1nt.todoapp.output;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Error {

    @JsonProperty("error-code")
    private int code;
    @JsonProperty("error-message")
    private String message;

}
