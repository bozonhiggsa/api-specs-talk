package io.github.q1nt.todoapp;


import static java.util.Collections.emptyList;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.q1nt.todoapp.entity.Tag;
import io.github.q1nt.todoapp.entity.Todo;
import io.github.q1nt.todoapp.input.CreateTodo;
import io.github.q1nt.todoapp.input.UpdateTodo;
import io.github.q1nt.todoapp.repository.TodoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;

public class TodoResourceTest extends BaseResourceTest {

    @Autowired
    TodoRepository todos;

    @Test
    public void listTodo() throws Exception {
        this.mockMvc.perform(get("/todos"))
                .andExpect(status().isOk());
    }

    @Test
    public void listByTag() throws Exception {
        this.mockMvc.perform(get("/todos")
                .param("tag", "work"))
                .andExpect(status().isOk());
    }

    @Test
    public void getById() throws Exception {
        Todo todo = pickSomeTodo();

        this.mockMvc.perform(get("/todos/{id}", todo.getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void getByIdNotFound() throws Exception {
        long id = Long.MIN_VALUE;

        this.mockMvc.perform(get("/todos/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    public void createTodo() throws Exception {
        CreateTodo request = new CreateTodo("my-test-todo", emptyList());

        MvcResult mvcResult = this.mockMvc.perform(post("/todos")
                .content(serialize(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andReturn();

        Todo result = deserialize(mvcResult.getResponse().getContentAsString(), Todo.class);

        Todo itemInDb = todos.findById(result.getId())
                .orElseThrow(() -> new AssertionError("expected todo created"));

        Assert.assertEquals(itemInDb.getName(), request.getName());
    }

    @Test
    public void updateTodo() throws Exception {
        Todo todo = pickSomeTodo();
        Long id = todo.getId();

        UpdateTodo request = new UpdateTodo();
        request.setDone(true);
        request.setName("updated name");

        this.mockMvc.perform(put("/todos/{id}", id)
                .content(serialize(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        Todo itemInDb = todos.findById(id)
                .orElseThrow(() -> new AssertionError("expected todo created"));

        Assert.assertEquals(request.getDone(), itemInDb.isDone());
        Assert.assertEquals(request.getName(), itemInDb.getName());
    }

    @Test
    public void deleteTodo() throws Exception {
        Todo todo = pickSomeTodo();
        Long id = todo.getId();

        this.mockMvc.perform(delete("/todos/{id}", id))
                .andExpect(status().isNoContent());

        Assert.assertFalse(todos.existsById(id));
    }

    private Todo pickSomeTodo() {
        return todos.findAll().iterator().next();
    }

}
