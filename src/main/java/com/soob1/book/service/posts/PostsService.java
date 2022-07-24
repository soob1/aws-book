package com.soob1.book.service.posts;

import com.soob1.book.domain.posts.Posts;
import com.soob1.book.domain.posts.PostsRepository;
import com.soob1.book.web.dto.PostsListResponse;
import com.soob1.book.web.dto.PostsResponse;
import com.soob1.book.web.dto.PostsSaveRequest;
import com.soob1.book.web.dto.PostsUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PostsService {

	private final PostsRepository postsRepository;

	@Transactional
	public Long save(PostsSaveRequest request) {
		return postsRepository.save(request.toEntity()).getId();
	}

	@Transactional
	public Long update(Long id, PostsUpdateRequest request) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
		posts.update(request.getTitle(), request.getContent());
		return id;
	}

	public PostsResponse findById(Long id) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
		return new PostsResponse(posts);
	}

	public List<PostsListResponse> findAll() {
		return postsRepository.findAllByOrderByIdDesc().stream()
				.map(PostsListResponse::new)
				.collect(Collectors.toList());
	}

	@Transactional
	public void delete(Long id) {
		Posts posts = postsRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id = " + id));
		postsRepository.delete(posts);
	}
}
