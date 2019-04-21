package ptit.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import ptit.bookstore.model.User;
import ptit.bookstore.utility.BreadcumInfo;
import ptit.bookstore.utility.CheckSession;

@Controller
public class HomeCtr {
	List<BreadcumInfo> listBreadCum;
	@PostConstruct
	public void init() {		
		listBreadCum = new ArrayList<BreadcumInfo>();
		BreadcumInfo homepage = new BreadcumInfo("Home", "/bookstore/index", true);
		listBreadCum.add(homepage);
	}
	
	
	@RequestMapping(value = {"/index", "/"})
	public String index(HttpSession session, ModelMap model) {
		if (!CheckSession.checkUserSession(session))
			return "redirect:/admin_login";	
		User user = (User) session.getAttribute("user");		
		model.addAttribute("user", user);	
						
		model.addAttribute("listBreadCum", listBreadCum);
		return "index";
	}
	

	@RequestMapping(value = "/signout")
	public String signout(HttpSession session) {
		session.removeAttribute("user");
		return "/auth/login";
	}
}
