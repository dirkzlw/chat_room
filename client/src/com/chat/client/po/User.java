package com.chat.client.po;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Ranger
 * @create 2019-12-18 14:01
 */
@Getter
@Setter
@ToString
public class User {
    private Integer userId;
    private String username;
    private String password;

    public User() {
    }

    public User(String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }
}
