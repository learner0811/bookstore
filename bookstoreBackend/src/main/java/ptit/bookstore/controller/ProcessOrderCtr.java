package ptit.bookstore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ProcessOrderCtr {
	@RequestMapping("/processorder/index")
	public ModelAndView mav() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/process_order/index");
		return mav;
	}
}
