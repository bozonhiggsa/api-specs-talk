package io.github.q1nt.todoapp.serialization;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JacksonAnnotationsInside
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(NON_NULL)
public @interface Json {

}
