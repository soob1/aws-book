package com.soob1.book.web;

import com.soob1.book.domain.posts.Posts;
import com.soob1.book.domain.posts.PostsRepository;
import com.soob1.book.web.dto.PostsSaveRequest;
import com.soob1.book.web.dto.PostsUpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 랜덤 포트 실행
class PostsApiTest {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private PostsRepository postsRepository;

	@AfterEach
	void tearDown() {
		postsRepository.deleteAll();
	}

	@Test
	@DisplayName("Posts 등록")
	void save() {
		// given
		String title = "title";
		String content = "content";
		PostsSaveRequest request = PostsSaveRequest.builder()
				.title(title)
				.content(content)
				.author("author")
				.build();

		String url = "http://localhost:" + port + "/api/v1/posts";

		// when
		ResponseEntity<Long> responseEntity = restTemplate.postForEntity(url, request, Long.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
	}

	@Test
	@DisplayName("Posts 수정")
	void update() {
		// given
		Posts savedPosts = postsRepository.save(Posts.builder()
				.title("title")
				.content("content")
				.author("author")
				.build());

		Long updateId = savedPosts.getId();
		String expectedTitle = "title2";
		String expectedContent = "content2";

		PostsUpdateRequest updateRequest = PostsUpdateRequest.builder()
				.title(expectedTitle)
				.content(expectedContent)
				.build();

		String url = "http://localhost:" + port + "/api/v1/posts/" + updateId;
		HttpEntity<PostsUpdateRequest> requestHttpEntity = new HttpEntity<>(updateRequest);

		// when
		ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.PUT, requestHttpEntity, Long.class);

		// then
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
	}
}