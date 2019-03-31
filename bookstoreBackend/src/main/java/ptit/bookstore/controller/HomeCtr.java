package ptit.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeCtr {
	
	@RequestMapping(value = {"/index", "/"})
	public String index() {
		return "index";
	}
}
