package io.coaton.user;

import io.coaton.user.controller.UserController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Testcontainers
public class UserControllerTests {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16.0")
            .withDatabaseName("user_db_test")
            .withUsername("postgres")
            .withPassword("postgres");

    @MockBean
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getNonExistingUser() throws Exception {
        String id="999";
        String getUserUrl="/user/"+id;
        mockMvc.perform(
                MockMvcRequestBuilders.get(getUserUrl)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError()
                );
    }

}
