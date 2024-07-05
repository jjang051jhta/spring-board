package com.jjang051.board.controller;

import com.jjang051.board.dto.AlertDto;
import com.jjang051.board.dto.BoardDto;
import com.jjang051.board.dto.DeleteBoardDto;
import com.jjang051.board.service.BoardService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/board")
@Slf4j

public class BoardController {


    @Autowired
    private BoardService boardService;

    @GetMapping("/write")
    public String write(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        model.addAttribute("title"," 글쓰기");
        //view resolver  template + board/write + .html
        return "board/write";
    }

//    @GetMapping("/delete/{id}")
//    public String delete(@PathVariable int id, Model model) {
//        DeleteBoardDto deleteBoardDto = DeleteBoardDto.builder()
//                                        .id(id)
//                                        .build();
//        model.addAttribute("deleteBoardDto",deleteBoardDto);
//        return "board/delete";
//    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        return "board/delete";
    }

    @GetMapping("/delete-param")
    public String deleteParam() {
        return "board/delete-param";
    }

    @PostMapping("/delete")
    public String deleteProcess(
            @ModelAttribute DeleteBoardDto paramDeleteBoardDto,
            RedirectAttributes redirectAttributes
    ) {
        DeleteBoardDto deleteBoardDto = DeleteBoardDto.builder()
                .id(paramDeleteBoardDto.getId())
                .password(paramDeleteBoardDto.getPassword())
                .build();
        int result = boardService.deleteBoard(deleteBoardDto);
        AlertDto alertDto = null;
        if(result>0) {
            alertDto = AlertDto.builder()
                    .title("OK")
                    .icon("warning")
                    .text("잘지워졌음")
                    .build();
            redirectAttributes.addFlashAttribute("alertDto",alertDto);
            return "redirect:/board/list";
        } else {
            alertDto = AlertDto.builder()
                    .title("FAIL")
                    .icon("error")
                    .text("패스워드 확인해주세요")
                    .build();
            redirectAttributes.addFlashAttribute("alertDto",alertDto);
            return "redirect:/board/delete/"+paramDeleteBoardDto.getId();
        }

    }


    @GetMapping("/list")
    public String list(Model model) {
        List<BoardDto> boardList = boardService.getAllBoard();
        model.addAttribute("boardList",boardList);
        return "board/list";
    }




    @GetMapping("/{id}")
    public String read(@PathVariable int id, Model model) {
        BoardDto boardDto = boardService.readBoard(id);
        log.info("boardDto==={}",boardDto);
        log.info("id==={}",id);
        model.addAttribute("boardDto",boardDto);
        return "board/view";
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
        int result = boardService.writeBoard(boardDto);
        return "redirect:/board/list";
    }
}
