package io.github.q1nt.todoapp.resource;

import io.github.q1nt.todoapp.entity.Tag;
import io.github.q1nt.todoapp.input.CreateTag;
import io.github.q1nt.todoapp.repository.TagRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@AllArgsConstructor
@RestController
@RequestMapping("/tags")
public class TagResource {

    private final TagRepository tags;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Collection<Tag> list() {
        return tags.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Tag create(@RequestBody CreateTag request) {
        return tags.saveAndFlush(new Tag(request.getName()));
    }

}
