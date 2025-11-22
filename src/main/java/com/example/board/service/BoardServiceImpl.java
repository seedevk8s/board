package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.board.dto.BoardDto;
import com.example.board.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;

	@Override
	public List<BoardDto> selectBoardList() {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardList();
	}

	@Override
	public void insertBoard(BoardDto boardDto) {
		// TODO Auto-generated method stub
		boardMapper.insertBoard(boardDto);
	}

	@Override
	public BoardDto selectBoardDetail(int boardIdx) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardDetail(boardIdx);
	}

}
