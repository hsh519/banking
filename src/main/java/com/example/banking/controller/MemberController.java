package com.example.banking.controller;

import com.example.banking.dto.MemberDto;
import com.example.banking.entity.Member;
import com.example.banking.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping(value = {"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping("/user/join")
    public String join(Model model) {
        model.addAttribute("memberDto", new MemberDto());
        return "joinForm";
    }

    @PostMapping("/user/join")
    public String join(@Validated MemberDto memberDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("memberDto", memberDto);
            return "joinForm";
        }

        Member joinMember = memberService.join(memberDto);
        return "redirect:/";
    }
}
