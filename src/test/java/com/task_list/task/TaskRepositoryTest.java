package com.task_list.task;

import com.task_list.task.entity.Task;
import com.task_list.task.repository.ITaskRepository;
import com.task_list.user.entity.MyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryTest {

    @Mock
    private ITaskRepository taskRepository;

    public Task task;
    private MyUser myUser;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id("67acf3507a2eda3e63e9c1e3")
                .title("Hola esto es un titulo")
                .description("hola esto es un parrafo de descripcion...")
                .status(Task.Status.SIN_REALIZAR)
                .dateCreated(LocalDateTime.now().toString())
                .dateUpdated(LocalDateTime.now().toString())
                .build();
        myUser = MyUser.builder()
                .id("67ae3311d875762b0042ed28")
                .fullName("Juan Jose Sierra Ortega")
                .email("juan@gmail.com")
                .password("12311")
                .dateCreated("2025/1/10 04:30")
                .build();
    }

    @Test
    void getTaskById(){
        String id = "67acf3507a2eda3e63e9c1e3";
        when(taskRepository.findTaskById(id)).thenReturn(Optional.of(task));
        Optional<Task> findTask = taskRepository.findTaskById(id);

        assertTrue(findTask.isPresent());
        assertEquals("67acf3507a2eda3e63e9c1e3", findTask.get().getId());
    }

    @Test
    void getAllTasksByEmail(){
        String email = "juanjose@gmail.com";
        when(taskRepository.findTaskByEmail(email)).thenReturn(Optional.of(task));
        Optional<Task> findTask = taskRepository.findTaskByEmail(email);

        assertEquals("67acf3507a2eda3e63e9c1e3", findTask.get().getId());
    }

    @Test
    void saveTaskTest() {
        when(taskRepository.save(task)).thenReturn(task);
        Task savedTask = taskRepository.save(task);

        assertEquals(task.getId(), savedTask.getId());
        assertEquals(task.getTitle(), savedTask.getTitle());
        assertEquals(task.getDescription(), savedTask.getDescription());
    }

    @Test
    void updateTaskByIdAndEmailTest() {
        Task taskForUpdate = Task.builder()
                .id("67acf3507a2eda3e63e9c1e3")
                .title("Hola esto es un titulo que debe ser cambiado")
                .description("hola esto es un parrafo de descripcion que debe ser cambiado...")
                .status(Task.Status.SIN_REALIZAR)
                .build();

        Task taskUpdate = Task.builder()
                .id("67acf3507a2eda3e63e9c1e3")
                .title("Hola esto es un titulo que debe ser cambiado")
                .description("hola esto es un parrafo de descripcion que debe ser cambiado...")
                .status(Task.Status.SIN_REALIZAR)
                .build();

        when(taskRepository.update("67acf3507a2eda3e63e9c1e3", taskForUpdate, myUser)).thenReturn(Optional.ofNullable(taskUpdate));
        Optional<Task> updatedTask = taskRepository.update("67acf3507a2eda3e63e9c1e3", taskForUpdate, myUser);

        assertEquals("67acf3507a2eda3e63e9c1e3", updatedTask.get().getId());
        assertEquals("Hola esto es un titulo que debe ser cambiado", updatedTask.get().getTitle());
        assertEquals("hola esto es un parrafo de descripcion que debe ser cambiado...", updatedTask.get().getDescription());
    }

    @Test
    void deleteTaskByIdAndEmailTest(){
        String id = "67acf3507a2eda3e63e9c1e3";
        when(taskRepository.delete(id)).thenReturn(true);
        assertTrue(taskRepository.delete(id));
    }

}
