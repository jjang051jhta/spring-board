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
        return boardDao.writeBoard(boardDto);
    }
    public BoardDto readBoard(int id) {
        return boardDao.readBoard(id);
    }
    public List<BoardDto> getAllBoard() {
        return boardDao.getAllBoard();
    }
}
