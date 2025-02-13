package com.task_list.task;

import com.task_list.exception.MyUserException;
import com.task_list.exception.TaskNotFoundException;
import com.task_list.task.entity.Task;
import com.task_list.task.entity.dto.TaskRequestDto;
import com.task_list.task.entity.dto.TaskRequestWithEmailUserDto;
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
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

    @Mock
    private ITaskService taskService;

    private TaskRequestDto taskRequestDto;
    private TaskResponseDto taskResponseDto;
    private TaskRequestWithEmailUserDto taskRequestWithEmailUserDto;
    private Task task;
    private MyUser myUser;

    @BeforeEach
    void setUp(){
        taskRequestDto = TaskRequestDto.builder()
                .title("Hola esto es un titulo")
                .description("hola esto es un parrafo de descripcion...")
                .priority("ALTA")
                .build();

        taskRequestWithEmailUserDto = TaskRequestWithEmailUserDto.builder()
                .emailUser("juan@gmail.com")
                .title("Hola esto es un titulo")
                .description("hola esto es un parrafo de descripcion...")
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
        when(taskService.save(taskRequestWithEmailUserDto)).thenReturn(taskResponseDto);
        TaskResponseDto responseDto = taskService.save(taskRequestWithEmailUserDto);

        assertEquals(responseDto, taskResponseDto);
        assertEquals(responseDto.title(), taskResponseDto.title());
        assertEquals(responseDto.description(), taskResponseDto.description());
        assertEquals(responseDto.dateCreated(), taskResponseDto.dateCreated());
    }

    @Test
    void updateTask() throws TaskNotFoundException, MyUserException {

        TaskRequestWithEmailUserDto taskToUpdate = TaskRequestWithEmailUserDto.builder()
                .emailUser("juan@gmail.com")
                .title("Cambie el titulo")
                .description("Cambie la descripcion")
                .build();

        TaskResponseDto taskUpdate = TaskResponseDto.builder()
                .title("Cambie el titulo")
                .description("Cambie la descripcion")
                .priority("BAJA")
                .build();

        when(taskService.update(task.getId(), taskToUpdate)).thenReturn(taskUpdate);
        TaskResponseDto responseDto = taskService.update(task.getId(), taskToUpdate);

        assertEquals(responseDto.title(), taskUpdate.title());
        assertEquals(responseDto.description(), taskUpdate.description());
        assertEquals(responseDto.priority(), taskUpdate.priority());
    }

    @Test
    void deleteTask() throws TaskNotFoundException {
        String id = task.getId();
        when(taskService.deleteById(id)).thenReturn(true);
        assertTrue(taskService.deleteById(id));
    }

}
