package com.soob1.book.web.dto;

import com.soob1.book.domain.posts.Posts;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostsListResponse {

	private Long id;
	private String title;
	private String author;
	private LocalDateTime updatedDate;

	public PostsListResponse(Posts posts) {
		this.id = posts.getId();
		this.title = posts.getTitle();
		this.author = posts.getAuthor();
		this.updatedDate = posts.getUpdatedDate();
	}
}
