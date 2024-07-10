package com.jjang051.board.dao;

import com.jjang051.board.dto.LoginDto;
import com.jjang051.board.dto.MemberDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberDao {
    //메서드 정의민 하면 됨
    int signin(MemberDto memberDto);

    int duplicateId(String userId);

    MemberDto login(String userId);


    MemberDto info(MemberDto memberDto);

    int deleteMember(MemberDto memberDto);
}
