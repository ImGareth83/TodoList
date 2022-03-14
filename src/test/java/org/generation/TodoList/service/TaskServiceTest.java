package org.generation.TodoList.service;

import org.generation.TodoList.entity.Task;
import org.generation.TodoList.entity.TaskRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

/*
To test service with H2 database
 */
@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase
public class TaskServiceTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        this.taskService = new TaskService(taskRepository);
    }

    @ParameterizedTest
    @DisplayName("Should passed when all the tasks are returned")
    @CsvFileSource(resources = "/task.csv", numLinesToSkip = 1)
    public void shouldFindAllTasks(String title, String description, LocalDateTime targetDateTime,LocalDateTime lastUpdateDateTime) {
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setTargetDateTime(targetDateTime);
        t.setLastUpdateDateTime(lastUpdateDateTime);

        entityManager.persist(t);

        List<Task> result=taskService.getTasks();
        assertNotNull(result);
        assertNotEquals(0,result.size());
    }

    //Testing Spring Data Repository (Optional)
    @ParameterizedTest
    @DisplayName("Should passed when task is saved")
    @CsvFileSource(resources = "/task.csv", numLinesToSkip = 1)
    public void shouldSaveTask(String title, String description, LocalDateTime targetDateTime,LocalDateTime lastUpdateDateTime) {
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setTargetDateTime(targetDateTime);
        t.setLastUpdateDateTime(lastUpdateDateTime);

        Task result=taskService.save(t);
        assertNotNull(result);
        assertTrue(0<result.getId());
    }

    //Testing Spring Data Repository (Optional)
    @ParameterizedTest
    @DisplayName("Should passed when task is deleted")
    @CsvFileSource(resources = "/task.csv", numLinesToSkip = 1)
    public void shouldDeleteTask(String title, String description, LocalDateTime targetDateTime,LocalDateTime lastUpdateDateTime) {
        Task t = new Task();
        t.setTitle(title);
        t.setDescription(description);
        t.setTargetDateTime(targetDateTime);
        t.setLastUpdateDateTime(lastUpdateDateTime);

        t=entityManager.persist(t);
        taskService.delete(t);
        entityManager.flush();

        Task result = entityManager.find(Task.class,t.getId());
        assertNull(result);

    }
}