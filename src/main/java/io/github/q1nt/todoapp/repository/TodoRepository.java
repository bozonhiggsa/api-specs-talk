package io.github.q1nt.todoapp.repository;

import io.github.q1nt.todoapp.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TodoRepository extends JpaRepository<Todo, Long> {

    Collection<Todo> findByTags_name(String tag);

}
