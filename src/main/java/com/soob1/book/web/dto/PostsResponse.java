package com.soob1.book.web.dto;

import com.soob1.book.domain.posts.Posts;
import lombok.Getter;

@Getter
public class PostsResponse {

	private Long id;
	private String title;
	private String content;
	private String author;

	public PostsResponse(Posts posts) {
		this.id = posts.getId();
		this.title = posts.getTitle();
		this.content = posts.getContent();
		this.author = posts.getAuthor();
	}
}
