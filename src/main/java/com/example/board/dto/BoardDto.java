package com.example.board.dto;

import java.util.List;

import lombok.Data;

@Data
public class BoardDto {
    private int boardIdx;
    private String title;
    private String contents;
    private int hitCnt;
    private String creatorId;
    private String createdDt;
    private String updatorId;
    private String updatedDt;

    private List<BoardFileDto> fileInfoList; 
}
