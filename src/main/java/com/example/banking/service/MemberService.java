package com.example.banking.service;

import com.example.banking.dto.MemberDto;
import com.example.banking.entity.Member;
import com.example.banking.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    public Member join(MemberDto memberDto) {
        String encodePassword = passwordEncoder.encode(memberDto.getPassword());
        Member member = new Member(memberDto.getName(), encodePassword, memberDto.getBirthday());
        memberRepository.save(member);

        return member;
    }
}
