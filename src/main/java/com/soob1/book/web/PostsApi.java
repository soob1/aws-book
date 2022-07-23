package com.soob1.book.web;

import com.soob1.book.service.posts.PostsService;
import com.soob1.book.web.dto.PostsResponse;
import com.soob1.book.web.dto.PostsSaveRequest;
import com.soob1.book.web.dto.PostsUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApi {

	private final PostsService postsService;

	@PostMapping("/api/v1/posts")
	public Long save(@RequestBody PostsSaveRequest request) {
		return postsService.save(request);
	}

	@PutMapping("/api/v1/posts/{id}")
	public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequest request) {
		return postsService.update(id, request);
	}

	@GetMapping("/api/v1/posts/{id}")
	public PostsResponse findById(@PathVariable Long id) {
		return postsService.findById(id);
	}
}
