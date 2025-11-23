package com.example.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;

@Mapper
public interface BoardMapper {

	List<BoardDto> selectBoardList();
	void insertBoard(BoardDto boardDto);
	BoardDto selectBoardDetail(int boardIdx);
	void updateHitCount(int boardIdx);
	void updateBoard(BoardDto boardDto);
	void deleteBoard(int boardIdx);
	void insertBoardFileList(List<BoardFileDto> fileInfoList);
	
	List<BoardFileDto> selectBoardFileList(int boardIdx);
	BoardFileDto selectBoardFileInfo(@Param("idx") int idx, @Param("boardIdx") int boardIdx) throws Exception;
}
