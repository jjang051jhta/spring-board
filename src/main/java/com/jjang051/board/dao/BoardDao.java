package com.jjang051.board.dao;

import com.jjang051.board.dto.BoardDto;
import com.jjang051.board.dto.DeleteBoardDto;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardDao {


    //메서드 정의민 하면 됨
    int writeBoard(BoardDto boardDto);


    BoardDto readBoard(int id);


    List<BoardDto> getAllBoard();

    int deleteBoard(DeleteBoardDto deleteBoardDto);

}
