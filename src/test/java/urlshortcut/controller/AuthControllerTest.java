package urlshortcut.controller;

import com.jayway.jsonpath.JsonPath;
import org.hamcrest.core.StringStartsWith;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import urlshortcut.Job4jUrlShortcutApplication;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static urlshortcut.controller.AuthController.HEADER_STRING;
import static urlshortcut.controller.AuthController.TOKEN_PREFIX;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    MockMvc mvc;

    @Test
    public void whenRegisterAndLoginThenGetJwt() throws Exception {
        MvcResult result = this.mvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"site\":\"facebook.com\"}"))
                .andExpect(status().isOk())
                .andReturn();
        String body = result.getResponse().getContentAsString();
        String password = JsonPath.parse(body).read("password");
        this.mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"login\" : \"facebook.com\", \"password\" : \"%s\"}", password)))
                .andExpectAll(status().isOk(),
                header().string(HEADER_STRING, new StringStartsWith(TOKEN_PREFIX)));

    }
}