package com.jjang051.board.service;

import com.jjang051.board.dao.MemberDao;
import com.jjang051.board.dto.CustomUserDetails;
import com.jjang051.board.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailService implements UserDetailsService {
    //시큐리티에서 제공해주는 로그인 프로세스
    private final MemberDao memberDao;


    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        MemberDto memberDto = memberDao.login(userId); //아이디만 검증하면 됨...
        //여기에 내가 로그인 하는 방법을 써놓으면 됨...

        if(memberDto!=null) {
            memberDto.getRole();
            return new CustomUserDetails(memberDto);
        }
        throw new UsernameNotFoundException("해당하는 멤버가 없습니다.");



        //return null;
    }
}
