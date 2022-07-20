package com.soob1.book.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class PostsRepositoryTest {

	@Autowired
	PostsRepository postsRepository;

	@AfterEach
	void cleanUp() {
		postsRepository.deleteAll();
	}

	@Test
	@DisplayName("게시글 저장 및 조회")
	void posts_save_and_find() {
		// given
		String title = "테스트 게시글";
		String content = "테스트 본문";

		Posts posts = Posts.builder()
				.title(title)
				.content(content)
				.author("sooyoon126@gmail.com")
				.build();
		postsRepository.save(posts);

		// when
		List<Posts> postsList = postsRepository.findAll();

		// then
		Posts findPosts = postsList.get(0);
		assertThat(findPosts.getTitle()).isEqualTo(title);
		assertThat(findPosts.getContent()).isEqualTo(content);
	}
}