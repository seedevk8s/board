package com.example.board.dto;

import lombok.Data;

@Data
public class BoardFileDto {
    private int idx;
    private int boardIdx;
    private String originalFileName;
    private String storedFilePath;
    private String fileSize;			// 천단위 콤마를 적용하기 위해 문자열로 정의 

}
