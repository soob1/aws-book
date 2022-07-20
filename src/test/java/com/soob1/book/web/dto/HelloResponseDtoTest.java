package com.soob1.book.web.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloResponseDtoTest {

	@Test
	@DisplayName("lombok 어노테이션 테스트")
	void lombok_test() {
		// given
		String name = "test";
		int amount = 1000;

		// when
		HelloResponseDto helloResponseDto = new HelloResponseDto(name, amount);

		// then
		assertThat(helloResponseDto.getName()).isEqualTo(name);
		assertThat(helloResponseDto.getAmount()).isEqualTo(amount);
	}

}