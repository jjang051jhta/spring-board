package com.jjang051.board.service;

import com.jjang051.board.dao.MemberDao;
import com.jjang051.board.dto.LoginDto;
import com.jjang051.board.dto.MemberDto;
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


    public int signin(MemberDto memberDto) {

        return memberDao.signin(memberDto);
    }

    public int duplicateId(String userId) {
        int result = memberDao.duplicateId(userId);
        return result;
    }

    public MemberDto login(LoginDto memberDto) {
        MemberDto loginMemberDto = memberDao.login(memberDto);
        return loginMemberDto;
    }


    public MemberDto info(MemberDto memberDto) {
        return memberDao.info(memberDto);
    }
}
