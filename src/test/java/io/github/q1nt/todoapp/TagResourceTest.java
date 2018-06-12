package io.github.q1nt.todoapp;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.github.q1nt.todoapp.entity.Tag;
import io.github.q1nt.todoapp.input.CreateTag;
import io.github.q1nt.todoapp.repository.TagRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

public class TagResourceTest extends BaseResourceTest {

    @Autowired
    TagRepository tags;

    @Test
    public void listTags() throws Exception {
        this.mockMvc.perform(get("/tags"))
                .andExpect(status().isOk());
    }

    @Test
    public void createTag() throws Exception {
        CreateTag request = new CreateTag("my-test-tag");

        MvcResult mvcResult = this.mockMvc.perform(post("/tags")
                .content(serialize(request))
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated())
                .andReturn();

        Tag result = deserialize(mvcResult.getResponse().getContentAsString(), Tag.class);

        Tag itemInDb = tags.findById(result.getId())
                .orElseThrow(() -> new AssertionError("expected tag created"));

        Assert.assertEquals(itemInDb.getName(), request.getName());
    }
}
