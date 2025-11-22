package com.example.board.service;

import java.util.List;

import com.example.board.dto.BoardDto;

public interface BoardService {

	List<BoardDto> selectBoardList();

	void insertBoard(BoardDto boardDto);

	BoardDto selectBoardDetail(int boardIdx);

}
