package com.jjang051.board.service;

import com.jjang051.board.dao.MemberDao;
import com.jjang051.board.dto.CustomUserDetails;
import com.jjang051.board.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final MemberDao memberDao;
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        MemberDto memberDto = memberDao.login(userId);
        if(memberDto!=null) {
            return new CustomUserDetails(memberDto);
     //CustomUserDetails는 읽기 전용 Dto임 여기에 username과 password등등이 들어감...
        }
        return null;
    }
}
