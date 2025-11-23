package com.example.board.controller;

import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;
import com.example.board.dto.BoardListResponse;
import com.example.board.service.BoardService;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class RestApiController {
    @Autowired
    private BoardService boardService;

//    @GetMapping("/board")
//    @Operation(summary="게시판 목록 조회", description="등록된 게시물 중에 삭제되지 않은 게시물을 목록 형태로 반환합니다.")    
//    public List<BoardDto> boardList() throws Exception {
//        return boardService.selectBoardList();
//    }
//    
    
    @GetMapping("/board")
    @Operation(summary="게시판 목록 조회", description="등록된 게시물 중에 삭제되지 않은 게시물을 목록 형태로 반환합니다.")
    public List<BoardListResponse> boardList() throws Exception {
    	List<BoardListResponse> results = new ArrayList<>();
		List<BoardDto> list = boardService.selectBoardList();
		for (BoardDto dto : list) {
			BoardListResponse res = new ModelMapper().map(dto, BoardListResponse.class);
			results.add(res);
		}
		return results;
    }
    
    

    @PostMapping(value = "/board", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void insertBoard(@RequestParam("board") String boardData, MultipartHttpServletRequest request) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        BoardDto board = objectMapper.readValue(boardData, BoardDto.class);
        boardService.insertBoard(board, request);
    }

//    
//    @GetMapping("/board/{boardIdx}")
//    @Parameter(name = "boardIdx", description="게시판 아이디", required=true)
//    public BoardDto boardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
//        return boardService.selectBoardDetail(boardIdx);
//    }
//    
    
    @GetMapping("/board/{boardIdx}")
    @Parameter(name = "boardIdx", description="게시판 아이디", required=true)
    public ResponseEntity<Object> boardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
    	BoardDto boardDto = null;
    	try {
    		boardDto = boardService.selectBoardDetail(boardIdx);
    	} catch(Exception e) {
    		Map<String, Object> result = new HashMap<>();
    		result.put("code", HttpStatus.NOT_FOUND.value());
    		result.put("name", HttpStatus.NOT_FOUND.name());
    		result.put("message", "일치하는 게시물이 존재하지 않습니다.");
    		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);    		
    	}
    	return ResponseEntity.status(HttpStatus.OK).body(boardDto);
    }


    
    @PutMapping("/board/{boardIdx}")
    public void updateBoard(@PathVariable("boardIdx") int boardIdx, @RequestBody BoardDto boardDto) throws Exception {
        boardDto.setBoardIdx(boardIdx);
        boardService.updateBoard(boardDto);
    }
    
    @DeleteMapping("/board/{boardIdx}")
    public void deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
        boardService.deleteBoard(boardIdx);
    }
    
    @GetMapping("/board/file/{boardIdx}/{idx}")
    public void downloadBoardFile(@PathVariable("boardIdx") int boardIdx, @PathVariable("idx") int idx, HttpServletResponse response) throws Exception {
        BoardFileDto boardFileDto = boardService.selectBoardFileInfo(idx, boardIdx);
        if (ObjectUtils.isEmpty(boardFileDto)) {
            return;
        }
        
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

