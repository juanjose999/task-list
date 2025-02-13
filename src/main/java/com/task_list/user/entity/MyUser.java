package com.task_list.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task_list.task.entity.Task;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "my_user")
public class MyUser {

    @Id
    private String id;
    private String fullName;
    private String email;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @DBRef
    private Set<Task> tasks;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String dateCreated;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String lastLogin;

    public MyUser(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.tasks = new HashSet<>();
        this.setDateCreated();
        this.setLastLogin();
    }


    public void setDateCreated() {
        this.dateCreated = LocalDateTime.now().toString();
    }

    public void setLastLogin() {
        this.lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void addTask(Task task) {
        if(this.tasks.isEmpty()) this.tasks = new HashSet<>();
        this.tasks.add(task);
    }

}

