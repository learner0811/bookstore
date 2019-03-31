package ptit.bookstore.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class BookCtr {
	@RequestMapping("/book/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/book/index");

		return mav;
	}
}
