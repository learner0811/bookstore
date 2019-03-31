package ptit.bookstore.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class AccountCtr {		
	
	@RequestMapping("/account/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/account/index");
		return mav;
	}

	@RequestMapping("/account/test")
	public ModelAndView test() throws IOException {
		ModelAndView mav = new ModelAndView();			
		// Create a new student object		

		mav.setViewName("/account/index");
		return mav;
	}

}
