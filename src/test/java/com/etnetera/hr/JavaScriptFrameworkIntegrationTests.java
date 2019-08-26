package com.etnetera.hr;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.fail;

/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class JavaScriptFrameworkIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getFrameworks_givenNoParameters() throws Exception {
        final String responseBody = getResourceAsString("/json/getFrameworks.json");
        mockMvc
            .perform(MockMvcRequestBuilders.get("/frameworks"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    public void getFrameworks_givenName() throws Exception {
        final String responseBody = getResourceAsString("/json/getFrameworks_name.json");
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/frameworks")
                .param("name", "Angular")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    @DirtiesContext
    public void addFramework() throws Exception {
        final String requestBody = getResourceAsString("/json/addFramework.json");
        final String responseBody = getResourceAsString("/json/addFramework_result.json");
        mockMvc
            .perform(
                MockMvcRequestBuilders.post("/frameworks")
                    .contentType("application/json")
                    .content(requestBody)
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/frameworks")
                    .param("name", "Meteor")
                    .param("version", "1.8")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    @DirtiesContext
    public void updateFramework() throws Exception {
        final String requestBody = getResourceAsString("/json/updateFramework.json");
        final String responseBody = getResourceAsString("/json/updateFramework_result.json");
        mockMvc
            .perform(
                    MockMvcRequestBuilders.put("/frameworks/{id}", 1L)
                            .contentType("application/json")
                            .content(requestBody)
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/frameworks")
                .param("name", "Angular")
                .param("version", "3.2.1")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    public void updateFramework_givenUnknownId() throws Exception {
        final String requestBody = getResourceAsString("/json/updateFramework.json");
        mockMvc
            .perform(
                MockMvcRequestBuilders.put("/frameworks/{id}", 12345L)
                .contentType("application/json")
                .content(requestBody)
            )
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void deleteFramework() throws Exception {
        mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/frameworks/{id}", 1L)
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc
            .perform(
                MockMvcRequestBuilders.get("/frameworks")
                    .param("name", "Angular")
                    .param("version", "1.2.3")
            )
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().json("[]"));
    }

    @Test
    public void deleteFramework_givenUnknownId() throws Exception {
        mockMvc
            .perform(
                MockMvcRequestBuilders.delete("/frameworks/{id}", 12345L)
            )
            .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    private static String getResourceAsString(String resourceName) {
        try {
            final Path path = Paths.get(JavaScriptFrameworkIntegrationTests.class.getResource(resourceName).toURI());
            return Files.lines(path).collect(Collectors.joining(System.lineSeparator()));
        } catch (URISyntaxException | IOException | NullPointerException e) {
            fail("Failed to load resource: " + resourceName, e);
        }
        //this point is never reached.
        return null;
    }

}
