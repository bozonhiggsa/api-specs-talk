package io.github.q1nt.todoapp.input;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.github.q1nt.todoapp.serialization.Json;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Json
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTag {

    @JsonProperty("name")
    private String name;

}
