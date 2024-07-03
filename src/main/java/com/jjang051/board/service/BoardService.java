package com.jjang051.board.service;

import com.jjang051.board.dao.BoardDao;
import com.jjang051.board.dto.BoardDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BoardService {
    private final BoardDao boardDao;

    public int writeBoard(BoardDto boardDto) {
        //비지니스 로직을 여기다가 작성합니다.
        //db에 데이터 입출력 mybatis / jpa
        return boardDao.writeBoard(boardDto);
    }
    public BoardDto readBoard(int id) {
        return boardDao.readBoard(id);
    }
    public List<BoardDto> getAllBoard() {
        return boardDao.getAllBoard();
    }

    public int deleteBoard(BoardDto boardDto) {
        //비지니스 로직을 여기다가 작성합니다.
        //db에 데이터 입출력 mybatis / jpa
        return boardDao.deleteBoard(boardDto);
    }

}
