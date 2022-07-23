package com.soob1.book.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class PostsUpdateRequest {

	private String title;
	private String content;

	@Builder
	public PostsUpdateRequest(String title, String content) {
		this.title = title;
		this.content = content;
	}
}
