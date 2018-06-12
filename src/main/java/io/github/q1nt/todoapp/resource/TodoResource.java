package io.github.q1nt.todoapp.resource;

import static java.util.Optional.ofNullable;

import io.github.q1nt.todoapp.entity.Todo;
import io.github.q1nt.todoapp.exception.NotFoundException;
import io.github.q1nt.todoapp.input.CreateTodo;
import io.github.q1nt.todoapp.input.UpdateTodo;
import io.github.q1nt.todoapp.repository.TagRepository;
import io.github.q1nt.todoapp.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
@RestController
@RequestMapping("/todos")
public class TodoResource {

    private final TodoRepository todos;
    private final TagRepository tags;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Todo> list(@RequestParam(value = "tag", required = false) String tag) {
        return ofNullable(tag)
                .map(todos::findByTags_name)
                .orElse(todos.findAll());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Todo create(@RequestBody CreateTodo request) {
        Todo item = new Todo();
        item.setName(request.getName());

        item.setTags(ofNullable(request.getTags())
                .map(tags::findAllByNameIn)
                .orElse(Collections.emptyList()));

        return todos.saveAndFlush(item);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Todo get(@PathVariable Long id) {
        return todos.findById(id).orElseThrow(() -> notFound(id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Todo update(@PathVariable Long id, @RequestBody UpdateTodo request) {
        Todo todo = todos.findById(id).orElseThrow(() -> notFound(id));
        ofNullable(request.getDone()).ifPresent(todo::setDone);
        ofNullable(request.getName()).ifPresent(todo::setName);
        ofNullable(request.getTags()).map(tags::findAllByNameIn).ifPresent(todo::setTags);
        return todos.saveAndFlush(todo);
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        todos.findById(id).orElseThrow(() -> notFound(id));
        todos.deleteById(id);
    }

    private NotFoundException notFound(Long id) {
        return new NotFoundException("todo with id " + id + " not found");
    }
}
