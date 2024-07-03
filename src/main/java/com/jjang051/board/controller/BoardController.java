package com.jjang051.board.controller;

import com.jjang051.board.dto.BoardDto;
import com.jjang051.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/board")
@Slf4j
@RequiredArgsConstructor
public class BoardController {


    private final BoardService boardService;

//    public BoardController(BoardService boardService) {
//        this.boardService = boardService;
//    }

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "board/write";
    }

    @PostMapping("/write")
    public String writeProcess(@Valid @ModelAttribute BoardDto boardDto, BindingResult bindingResult) {
        //dao 들고와서 db에 저장하면된다. mybatis, jpa
        if(bindingResult.hasErrors()){
            //@ModelAttribute를 쓰면 자동으로 넘어온 값을 가지고 forward를 한다.
            //model.addAttribute("boardDto",jfkjdskfld)
            return "board/write";
        }
        log.info("title==={}",boardDto.getTitle());
        log.info("content==={}",boardDto.getContent());
        boardService.writeBoard(boardDto);

        return "redirect:/";
    }
}
