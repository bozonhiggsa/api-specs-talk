package io.github.q1nt.todoapp.resource;

import static java.util.Collections.singleton;

import io.github.q1nt.todoapp.exception.NotFoundException;
import io.github.q1nt.todoapp.output.Error;
import io.github.q1nt.todoapp.output.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@Slf4j
@ControllerAdvice
public class ErrorHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorResponse handle(NotFoundException e) {
        log.error("not found happened", e);
        return new ErrorResponse(singleton(
                new Error(404, e.getMessage())
        ));
    }
}
