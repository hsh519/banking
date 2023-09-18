package com.example.banking.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String password;
    private String birthday;

    public Member(String name, String password, String birthday) {
        this.name = name;
        this.password = password;
        this.birthday = birthday;
    }
}
