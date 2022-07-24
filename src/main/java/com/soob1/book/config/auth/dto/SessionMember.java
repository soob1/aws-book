package com.soob1.book.config.auth.dto;

import com.soob1.book.domain.member.Member;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionMember implements Serializable {

	private String name;
	private String email;
	private String picture;

	public SessionMember(Member member) {
		this.name = member.getName();
		this.email = member.getEmail();
		this.picture = member.getPicture();
	}
}
