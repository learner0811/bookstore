package ptit.bookstore.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptit.bookstore.model.Account;
import ptit.bookstore.model.User;
import ptit.bookstore.service.LoginService;

@Controller
public class AuthController {
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/admin_login")
	public String login() {
		return "/auth/login";
	}
	
	@RequestMapping(value = "/admin_login", method = RequestMethod.POST)
	public String doLogin(@ModelAttribute Account account, RedirectAttributes redirect, HttpSession session) {
		User user = loginService.adminDoLogin(account);
		System.out.println("user is " + user);
		if (user == null) {
			redirect.addFlashAttribute("loginMsg", "User name or pass is incorrect");
			return "redirect:/admin_login";
		}
		session.setAttribute("user", user);
		session.setMaxInactiveInterval(30*60);
		return "redirect:/index";
	}	
}
