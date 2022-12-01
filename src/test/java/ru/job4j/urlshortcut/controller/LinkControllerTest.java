package ru.job4j.urlshortcut.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.job4j.urlshortcut.Job4jUrlShortcutApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static ru.job4j.urlshortcut.controller.AuthController.HEADER_STRING;
import static ru.job4j.urlshortcut.controller.LinkController.REDIRECT_HEADER;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
class LinkControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void whenRegisterAndLoginAndConvertWithJwtThenGetCode() throws Exception {
        MvcResult result1 = this.mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"site\":\"yahoo.com\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String body = result1.getResponse().getContentAsString();
        String password = JsonPath.parse(body).read("password");
        MvcResult result2 = this.mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"login\" : \"yahoo.com\", \"password\" : \"%s\"}", password)))
                .andExpect(status().isOk())
                .andReturn();
        String jwt = result2.getResponse().getHeader(HEADER_STRING);
        this.mvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"google.com\"}")
                        .header(HEADER_STRING, jwt))
                .andExpectAll(status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("code").isString());
    }

    @Test
    public void whenRegisterAndLoginAndConvertWithJwtAndRedirectThenRedirect() throws Exception {
        MvcResult result1 = this.mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"site\":\"youtube.com\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String body = result1.getResponse().getContentAsString();
        String password = JsonPath.parse(body).read("password");
        MvcResult result2 = this.mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"login\" : \"youtube.com\", \"password\" : \"%s\"}", password)))
                .andExpect(status().isOk())
                .andReturn();
        String jwt = result2.getResponse().getHeader(HEADER_STRING);
        MvcResult result3 = this.mvc.perform(post("/convert")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\":\"google.com\"}")
                .header(HEADER_STRING, jwt))
                .andExpect(status().isOk())
                .andReturn();
        String body3 = result3.getResponse().getContentAsString();
        String generatedCode = JsonPath.parse(body3).read("code");
        this.mvc.perform(get("/redirect/{code}", generatedCode))
                .andExpectAll(status().isFound(),
                        header().string(REDIRECT_HEADER, "http://google.com"));
    }

    @Test
    public void whenGetLinks2TimesThenStatistics2() throws Exception {
        MvcResult result1 = this.mvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"site\":\"site.com\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String body = result1.getResponse().getContentAsString();
        String password = JsonPath.parse(body).read("password");
        MvcResult result2 = this.mvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"login\" : \"site.com\", \"password\" : \"%s\"}", password)))
                .andExpect(status().isOk())
                .andReturn();
        String jwt = result2.getResponse().getHeader(HEADER_STRING);
        MvcResult result3 = this.mvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"url\":\"google.com\"}")
                        .header(HEADER_STRING, jwt))
                .andExpect(status().isOk())
                .andReturn();
        String body3 = result3.getResponse().getContentAsString();
        String generatedCode = JsonPath.parse(body3).read("code");
        this.mvc.perform(get("/redirect/{code}", generatedCode));
        this.mvc.perform(get("/redirect/{code}", generatedCode));
        this.mvc.perform(get("/statistic")
                .header(HEADER_STRING, jwt))
                .andExpectAll(status().isOk(),
                        content().contentType("application/json"),
                        content().json("[{\"url : google.com\":\"total : 2\"}]"));

    }
}