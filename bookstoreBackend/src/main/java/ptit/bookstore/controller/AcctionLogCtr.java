package ptit.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AcctionLogCtr {
	@RequestMapping("/actionlog/index")
	public ModelAndView mav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/action_log/index");
		return mav;
	}
}
