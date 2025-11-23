package com.example.board.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;
import com.example.board.service.BoardService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
	public String insertBoard(BoardDto boardDto,  MultipartHttpServletRequest request) throws Exception {
		boardService.insertBoard(boardDto, request);
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
	

	@PostMapping("/updateBoard.do")
	public String updateBoard(BoardDto boardDto) throws Exception {
		boardService.updateBoard(boardDto);
		return "redirect:/board/boardList.do";
	}
	
	@PostMapping("/deleteBoard.do")
	public String deleteBoard(@RequestParam("boardIdx") int boardIdx) throws Exception {
		boardService.deleteBoard(boardIdx);
		return "redirect:/board/boardList.do";
	}
	
    @GetMapping("/downloadBoardFile.do")
    public void downloadBoardFile(@RequestParam("idx") int idx, @RequestParam("boardIdx") int boardIdx, HttpServletResponse response) throws Exception {
        // idx와 boardIdx가 일치하는 파일 정보를 조회
        BoardFileDto boardFileDto = boardService.selectBoardFileInfo(idx, boardIdx);
        if (ObjectUtils.isEmpty(boardFileDto)) {
            return;
        }
        
        // 원본 파일 저장 위치에서 파일을 읽어서 호출한 곳으로 첨부파일을 응답으로 전달
        Path path = Paths.get(boardFileDto.getStoredFilePath());
        byte[] file = Files.readAllBytes(path); 
        
        response.setContentType("application/octet-stream");
        response.setContentLength(file.length);
        response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(boardFileDto.getOriginalFileName(), "UTF-8") + "\";");
        response.setHeader("Content-Transfer-Encoding", "binary");
        
        response.getOutputStream().write(file);
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }
	
}










