package org.generation.TodoList.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.generation.TodoList.entity.Task;
import org.generation.TodoList.service.TaskService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers=TaskController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TaskControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private TaskService taskService;

    @ParameterizedTest
    @DisplayName("Should passed when all the tasks are returned")
    @CsvFileSource(resources = "/task.csv", numLinesToSkip = 1)
    public void shouldFindAllTasks(String title, String description, LocalDateTime targetDateTime, LocalDateTime lastUpdateDateTime) throws Exception {

        assertNotNull(taskService);
        assertNotNull(mvc);

        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setTargetDateTime(targetDateTime);
        t.setLastUpdateDateTime(lastUpdateDateTime);

        when(taskService.getTasks()).thenReturn(Arrays.asList(t));

        mvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{}]"))
//                .andExpect(jsonPath("$.title").isNotEmpty())
        ;
    }

    @ParameterizedTest
    @DisplayName("Should passed when task is saved")
    @CsvFileSource(resources = "/task.csv", numLinesToSkip = 1)
    public void shouldSaveTask(String title, String description, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime targetDateTime, @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime lastUpdateDateTime) throws Exception {
        assertNotNull(taskService);
        assertNotNull(mvc);

        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setTargetDateTime(targetDateTime);
        t.setLastUpdateDateTime(lastUpdateDateTime);

        t.setId(8888);
        when(taskService.save(t)).thenReturn(t);

        mvc.perform(post("/tasks")
//                        .content("{\"id\": \"9999\"}")
                        .content(asJsonString(t))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())

        ;
    }

    public static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(obj);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @ParameterizedTest
    @DisplayName("Should passed when task is saved")
    @CsvFileSource(resources = "/task.csv", numLinesToSkip = 1)
    public void shouldSaveTasks(String title, String description, LocalDateTime targetDateTime, LocalDateTime lastUpdateDateTime) throws Exception {
        assertNotNull(taskService);
        assertNotNull(mvc);

        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setTargetDateTime(targetDateTime);
        t.setLastUpdateDateTime(lastUpdateDateTime);

        t.setId(8888);
        when(taskService.save(t)).thenReturn(t);

        mvc.perform(post("/tasks")
//                        .content("{\"id\": \"9999\"}")
                        .content(asJsonString(t))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").exists())

        ;
    }

}