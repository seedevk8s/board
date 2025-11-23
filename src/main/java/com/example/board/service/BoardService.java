package com.example.board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;

public interface BoardService {

	List<BoardDto> selectBoardList();
	void insertBoard(BoardDto boardDto);
	void insertBoard(BoardDto boardDto, MultipartHttpServletRequest request) throws Exception;
	BoardDto selectBoardDetail(int boardIdx);
	void updateBoard(BoardDto boardDto);
	void deleteBoard(int boardIdx);
	BoardFileDto selectBoardFileInfo(int idx, int boardIdx) throws Exception;


}
