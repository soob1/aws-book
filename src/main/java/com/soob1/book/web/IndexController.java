package com.soob1.book.web;

import com.soob1.book.config.auth.LoginMember;
import com.soob1.book.config.auth.dto.SessionMember;
import com.soob1.book.service.posts.PostsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {

	private final PostsService postsService;

	@GetMapping("/")
	public String index(Model model, @LoginMember SessionMember member) {
		model.addAttribute("posts", postsService.findAll());
		if (member != null) {
			model.addAttribute("memberName", member.getName());
		}
		return "index";
	}

	@GetMapping("/posts/save")
	public String postsSave() {
		return "posts-save";
	}

	@GetMapping("/posts/update/{id}")
	public String postsUpdate(@PathVariable Long id, Model model) {
		model.addAttribute("post", postsService.findById(id));
		return "posts-update";
	}
}
