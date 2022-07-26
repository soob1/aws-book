package com.soob1.book.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.soob1.book.domain.posts.Posts;
import com.soob1.book.domain.posts.PostsRepository;
import com.soob1.book.web.dto.PostsSaveRequest;
import com.soob1.book.web.dto.PostsUpdateRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(roles = "USER")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // 랜덤 포트 실행
class PostsApiTest {

	@LocalServerPort
	private int port;

	@Autowired
	private WebApplicationContext context;

	@Autowired
	private PostsRepository postsRepository;

	private MockMvc mvc;

	@BeforeEach
	void setUp() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}

	@AfterEach
	void tearDown() {
		postsRepository.deleteAll();
	}

	@Test
	@DisplayName("Posts 등록")
	void save() throws Exception {
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
		mvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(request)))
				.andExpect(status().isOk());

		// then
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(title);
		assertThat(all.get(0).getContent()).isEqualTo(content);
	}

	@Test
	@DisplayName("Posts 수정")
	void update() throws Exception {
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
		mvc.perform(put(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(updateRequest)))
				.andExpect(status().isOk());

		// then
		List<Posts> all = postsRepository.findAll();
		assertThat(all.get(0).getTitle()).isEqualTo(expectedTitle);
		assertThat(all.get(0).getContent()).isEqualTo(expectedContent);
	}

	@Test
	@DisplayName("Posts 삭제")
	void deletePost() throws Exception {
		// given
		Posts savedPosts = postsRepository.save(Posts.builder()
				.title("title")
				.content("content")
				.author("author")
				.build());

		Long id = savedPosts.getId();

		String url = "http://localhost:" + port + "/api/v1/posts/" + id;

		// when
		mvc.perform(delete(url))
				.andExpect(status().isOk());

		// then
		List<Posts> all = postsRepository.findAll();
		assertThat(all).isEmpty();
	}
}