package com.example.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.dto.BoardDto;
import com.example.board.service.BoardService;

@Controller
@RequestMapping("/board")
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/boardList.do")
	public ModelAndView boardList() throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		List<BoardDto> list = boardService.selectBoardList();
		mv.addObject("boardList", list);
		
		return mv;
	}
	
	// 글쓰기 화면 요청을 처리하는 메서드
	@GetMapping("/boardWrite.do")
	public String boardWrite() throws Exception {
		return "/board/boardWrite";
	}
	
	// 글저장을 처리하는 메서드
	@PostMapping("/insertBoard.do")
	public String insertBoard(BoardDto boardDto) throws Exception {
		boardService.insertBoard(boardDto);
		return "redirect:/board/boardList.do";
	}
	
	// 게시글 상세 조회 저리하는 메서드 
	// /board/boardDetail.do?boardIdx=1
	@GetMapping("/boardDetail.do")
	public ModelAndView boardDetail(@RequestParam("boardIdx") int boardIdx) throws Exception {
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		
		BoardDto board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	

}










