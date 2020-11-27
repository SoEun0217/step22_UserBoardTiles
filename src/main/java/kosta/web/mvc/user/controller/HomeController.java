package kosta.web.mvc.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String index() {
		return "main/index"; //1.beannameresolver 2.tilesview(이런 패턴이 있는가?)
	}
}
