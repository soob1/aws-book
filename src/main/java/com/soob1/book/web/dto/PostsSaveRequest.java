package com.soob1.book.web.dto;

import com.soob1.book.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsSaveRequest {

	private String title;

	private String content;

	private String author;

	@Builder
	public PostsSaveRequest(String title, String content, String author) {
		this.title = title;
		this.content = content;
		this.author = author;
	}

	public Posts toEntity() {
		return Posts.builder()
				.title(title)
				.content(content)
				.author(author)
				.build();
	}
}
