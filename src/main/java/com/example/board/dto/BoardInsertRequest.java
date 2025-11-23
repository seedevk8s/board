package com.example.board.dto;

import lombok.Data;

@Data
public class BoardInsertRequest {
	private String title;
	private String contents;
}
