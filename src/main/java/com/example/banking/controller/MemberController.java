package com.example.banking.controller;

import com.example.banking.security.jwt.TokenInfo;
import com.example.banking.dto.MemberJoin;
import com.example.banking.dto.MemberLogin;
import com.example.banking.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = {"", "/"})
    public String index() {
        return "index";
    }

    @ResponseBody
    @PostMapping("/user/join")
    public ResponseEntity<String> join(@Validated @RequestBody MemberJoin memberJoin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
             return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        if (memberService.join(memberJoin) == null) {
            return new ResponseEntity<>("이미 가입된 이메일입니다.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("회원가입 성공", HttpStatus.CREATED);
    }

    @ResponseBody
    @PostMapping(value = "/user/login")
    public Object login(@Validated @RequestBody MemberLogin memberLogin, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        }
        TokenInfo tokenInfo = memberService.login(memberLogin);
        return tokenInfo;
    }
}
