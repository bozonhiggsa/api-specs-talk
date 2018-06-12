package io.github.q1nt.todoapp;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

import io.github.q1nt.todoapp.entity.Tag;
import io.github.q1nt.todoapp.entity.Todo;
import io.github.q1nt.todoapp.repository.TagRepository;
import io.github.q1nt.todoapp.repository.TodoRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class Initializer implements CommandLineRunner {

    private final TagRepository tags;
    private final TodoRepository todos;

    @Override
    public void run(String... args) throws Exception {
        Tag family = new Tag("family");
        Tag work = new Tag("work");
        Tag talk = new Tag("talk");

        tags.saveAll(
                asList(family, work, talk)
        );

        todos.saveAll(
                asList(
                        Todo.builder().name("Prepare presentation").tags(asList(work, talk)).build(),
                        Todo.builder().name("Create app").tags(asList(work, talk)).build(),
                        Todo.builder().name("Finish task").tags(singletonList(work)).build(),
                        Todo.builder().name("Call mom").tags(singletonList(family)).build()
                )
        );

    }
}
