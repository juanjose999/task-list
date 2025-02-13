package com.task_list.user.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.task_list.task.entity.Task;
import lombok.Builder;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "my_user")
public class MyUser implements UserDetails {
    public enum Role {
        ADMIN, USER
    }
    @Id
    private String id;
    private String fullName;
    @Indexed(unique = true)
    private String email;
    private String password;
    @DBRef
    private Set<Task> tasks = new HashSet<>();
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
        this.dateCreated = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
    }

    public void setLastLogin() {
        this.lastLogin = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    public void addTask(Task task) {
        if(this.tasks.isEmpty()) this.tasks = new HashSet<>();
        this.tasks.add(task);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(String.valueOf(Role.USER)));
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

