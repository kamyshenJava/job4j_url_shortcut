package ru.job4j.urlshortcut.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.urlshortcut.Job4jUrlShortcutApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
class RegControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void whenRegisterThenGetTrueAndPassword() throws Exception {
        this.mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"site\":\"job4j.ru\"}"))
                .andExpectAll(status().isOk(),
                content().contentType("application/json"),
                jsonPath("registration").value("true"),
                jsonPath("login").value("job4j.ru"),
                jsonPath("password").isString());
    }

    @Test
    public void whenRegisterWithSameLoginThenGetFalse() throws Exception {
        this.mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"site\":\"mail.ru\"}"));
        this.mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"site\":\"mail.ru\"}"))
                .andExpectAll(status().isOk(),
                content().contentType("application/json"),
                jsonPath("registration").value("false"),
                jsonPath("login").value("mail.ru"),
                jsonPath("password").doesNotExist());
    }
}