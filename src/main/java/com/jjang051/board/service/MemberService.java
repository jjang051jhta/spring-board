package com.jjang051.board.service;

import com.jjang051.board.dao.MemberDao;
import com.jjang051.board.dto.LoginDto;
import com.jjang051.board.dto.MemberDto;
import com.jjang051.board.dto.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberDao memberDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public int signin(MemberDto memberDto) {

        MemberDto signInDto = MemberDto.builder()
                .email(memberDto.getEmail())
                .userId(memberDto.getUserId())
                .userName(memberDto.getUserName())
                .role(Role.ADMIN.getRole())
                .password(bCryptPasswordEncoder.encode(memberDto.getPassword()))
                .build();
        log.info("signInDto==={}",signInDto.getRole());
        log.info("Role.ADMIN.name()==={}",Role.ADMIN.getRole());
        return memberDao.signin(signInDto);
    }

    public int duplicateId(String userId) {
        int result = memberDao.duplicateId(userId);
        return result;
    }

    public MemberDto login(LoginDto memberDto) {
        MemberDto loginMemberDto = memberDao.login(memberDto.getUserId());
        return loginMemberDto;
    }


    public MemberDto info(MemberDto memberDto) {
        return memberDao.info(memberDto);
    }

    public int deleteMember(MemberDto memberDto) {
        return memberDao.deleteMember(memberDto);
    }
}
