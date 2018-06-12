package io.github.q1nt.todoapp.repository;

import io.github.q1nt.todoapp.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Collection<Tag> findAllByNameIn(Collection<String> name);
}
