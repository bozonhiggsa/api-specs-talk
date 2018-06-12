package io.github.q1nt.todoapp.entity;

import io.github.q1nt.todoapp.serialization.Json;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Json
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "todos")
public class Todo {

    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String name;
    @Column
    private boolean done;
    @Column
    private Date created;
    @Column
    private Date updated;
    @ManyToMany
    private Collection<Tag> tags;

    @PrePersist
    @PreUpdate
    public void prePersistOrUpdate() {
        updated = new Date();
        if (created == null) {
            created = updated;
        }
    }

}
