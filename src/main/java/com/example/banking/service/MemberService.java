package com.example.banking.service;

import com.example.banking.security.jwt.TokenInfo;
import com.example.banking.security.jwt.TokenProvider;
import com.example.banking.dto.MemberJoin;
import com.example.banking.dto.MemberLogin;
import com.example.banking.entity.Member;
import com.example.banking.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Transactional
    public Member join(MemberJoin memberJoin) {
        String encodePassword = passwordEncoder.encode(memberJoin.getPassword());

        if (checkDuplicateEmail(memberJoin)) {
            return null;
        }

        Member member = new Member(memberJoin.getName(), memberJoin.getEmail(), encodePassword, memberJoin.getBirthday());
        memberRepository.save(member);
        return member;
    }

    @Transactional
    public TokenInfo login(MemberLogin memberLogin) {
        String email = memberLogin.getEmail();
        String rawPassword = memberLogin.getPassword();
        Optional<Member> findMember = memberRepository.findByEmail(email);

        if (findMember.isEmpty()) {
            return null;
        }

        if (!passwordEncoder.matches(rawPassword, findMember.get().getPassword())) {
            return null;
        }

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(email, rawPassword);
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        return tokenProvider.generateToken(authentication);
    }

    private boolean checkDuplicateEmail(MemberJoin memberJoin) {
        return memberRepository.findByEmail(memberJoin.getEmail()).isPresent();
    }
}