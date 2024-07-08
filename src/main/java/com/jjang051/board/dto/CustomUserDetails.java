package com.jjang051.board.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetails implements UserDetails {

    private final MemberDto memberDto;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // admin, user
        // role 정보는 여기서 뽑아 쓸 수 있다.
        //role 정보
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return memberDto.getRole();
            }
        });
        return collection;
    }

    @Override
    public String getPassword() {
        return memberDto.getPassword();
    }

    @Override
    public String getUsername() {
        //이건 신경 안써도 됨   userId
        return memberDto.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
