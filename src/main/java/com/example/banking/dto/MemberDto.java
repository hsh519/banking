package com.example.banking.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Getter @Setter
public class MemberDto {

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty
    @Size(min = 8, message = "비밀번호는 최소 8자 이상이어야 합니다.")
    private String password;

    @NotEmpty(message = "생년월일을 입력해주세요.")
    private String birthday;
}
