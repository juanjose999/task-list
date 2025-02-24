package com.task_list.task.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task_list.user.entity.MyUser;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "task")
public class Task {
    public enum Status {
        SIN_REALIZAR,
        REALIZADA,
        EN_PROCESO
    }
    public enum Priority {
        ALTA, BAJA
    }
    @Id
    private String id;
    private String title;
    private String description;
    private Status status;
    private Priority priority;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String dateCreated;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String dateUpdated;
    @DBRef
    private MyUser user;

    public void setDateCreated() {
        this.dateCreated = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }
    public void setDateUpdated() {
        this.dateCreated = LocalDateTime.now().toString();
    }

}
