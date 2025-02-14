package com.task_list.jwt.token.entity;

import com.task_list.user.entity.MyUser;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "token")
public class Token {
    @Id
    private String id;
    private String token;
    private boolean expired;
    private boolean revoked;
    @DBRef
    private MyUser myUser;
}
