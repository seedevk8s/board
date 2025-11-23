package com.example.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.example.board.common.FileUtils;
import com.example.board.dto.BoardDto;
import com.example.board.dto.BoardFileDto;
import com.example.board.mapper.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Transactional
@Service
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;

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

	@Transactional
	@Override
	public BoardDto selectBoardDetail(int boardIdx) {
		// TODO Auto-generated method stub
		boardMapper.updateHitCount(boardIdx);
		//int i = 10 / 0;
		
		BoardDto boardDto = boardMapper.selectBoardDetail(boardIdx);					//나중추가
		List<BoardFileDto> boardFileList = boardMapper.selectBoardFileList(boardIdx);   //나중추가
		boardDto.setFileInfoList(boardFileList);		                                //나중추가
		
		//return boardMapper.selectBoardDetail(boardIdx);
		return boardDto;																//나중추가	
	}

	@Override
	public void updateBoard(BoardDto boardDto) {
		// TODO Auto-generated method stub
		boardMapper.updateBoard(boardDto);
	}

	@Override
	public void deleteBoard(int boardIdx) {
		// TODO Auto-generated method stub
		boardMapper.deleteBoard(boardIdx);	
	}

	@Override
	public void insertBoard(BoardDto boardDto, MultipartHttpServletRequest request)  throws Exception {
		// TODO Auto-generated method stub
//		// 요청으로부터 파일을 추출
//		if (!ObjectUtils.isEmpty(request)) {
//			// <input type="file" name="이 속성에 명시된 이름" />
//			Iterator<String> fileTagNames = request.getFileNames();
//			while (fileTagNames.hasNext()) {
//				String fileTagName = fileTagNames.next();
//				// 하나의 <input type="file"> 태그를 통해서 전달된 파일들을 가져옮
//				List<MultipartFile> files = request.getFiles(fileTagName);
//				for (MultipartFile file : files) {
//					log.debug("File Information");
//					log.debug("- file name: " + file.getOriginalFilename());
//					log.debug("- file size: " + file.getSize());
//					log.debug("- content type: " + file.getContentType());
//				}
//			}
//		}		
		
		boardMapper.insertBoard(boardDto);
		
		// 첨부 파일을 저장하고 첨부 파일 정보를 반환
		List<BoardFileDto> fileInfoList = fileUtils.parseFileInfo(boardDto.getBoardIdx(), request);
		
		// 첨부 파일 정보를 저장
		if (!CollectionUtils.isEmpty(fileInfoList)) {
			boardMapper.insertBoardFileList(fileInfoList);
		}		
		
		
	}

	@Override
	public BoardFileDto selectBoardFileInfo(int idx, int boardIdx) throws Exception {
		// TODO Auto-generated method stub
		return boardMapper.selectBoardFileInfo(idx, boardIdx);
	}

}



