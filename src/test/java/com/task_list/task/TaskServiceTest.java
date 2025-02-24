package com.task_list.task;

import com.task_list.exception.JwtException;
import com.task_list.exception.MyUserException;
import com.task_list.exception.TaskNotFoundException;
import com.task_list.task.entity.Task;
import com.task_list.task.entity.dto.TaskRequestDto;
import com.task_list.task.entity.dto.TaskResponseDto;
import com.task_list.task.service.ITaskService;
import com.task_list.user.entity.MyUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private ITaskService taskService;

    private TaskRequestDto taskRequestDto;
    private TaskResponseDto taskResponseDto;
    private Task task;
    private MyUser myUser;
    private String emailUser;

    @BeforeEach
    void setUp(){
        emailUser = "juan@gmail.com";
        taskRequestDto = TaskRequestDto.builder()
                .title("Hola esto es un titulo")
                .description("hola esto es un parrafo de descripcion...")
                .priority("ALTA")
                .build();

        taskResponseDto = TaskResponseDto.builder()
                .title("Hola esto es un titulo")
                .description("hola esto es un parrafo de descripcion...")
                .status("SIN_REALIZAR")
                .priority(String.valueOf(Task.Priority.ALTA))
                .dateCreated(LocalDateTime.now().toString())
                .build();

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
    void findAllTaskByEmail(){

    }

    @Test
    void createTask() throws MyUserException {
        when(taskService.save(taskRequestDto, emailUser)).thenReturn(taskResponseDto);
        TaskResponseDto responseDto = taskService.save(taskRequestDto,emailUser);

        assertEquals(responseDto, taskResponseDto);
        assertEquals(responseDto.title(), taskResponseDto.title());
        assertEquals(responseDto.description(), taskResponseDto.description());
        assertEquals(responseDto.dateCreated(), taskResponseDto.dateCreated());
    }

    @Test
    void updateTask() throws TaskNotFoundException, MyUserException {

        TaskRequestDto taskToUpdate = TaskRequestDto.builder()
                .title("Cambie el titulo")
                .description("Cambie la descripcion")
                .build();

        TaskResponseDto taskUpdate = TaskResponseDto.builder()
                .title("Cambie el titulo")
                .description("Cambie la descripcion")
                .priority("BAJA")
                .build();

        when(taskService.update(task.getId(), taskToUpdate, emailUser)).thenReturn(taskUpdate);
        TaskResponseDto responseDto = taskService.update(task.getId(), taskToUpdate,emailUser);

        assertEquals(responseDto.title(), taskUpdate.title());
        assertEquals(responseDto.description(), taskUpdate.description());
        assertEquals(responseDto.priority(), taskUpdate.priority());
    }

    @Test
    void deleteTask() throws TaskNotFoundException, MyUserException, JwtException {
        String id = task.getId();
        when(taskService.deleteById(id,"67b7816bb48b3c38d4e38534")).thenReturn(true);
        assertTrue(taskService.deleteById(id,"67b7816bb48b3c38d4e38534"));
    }

}
