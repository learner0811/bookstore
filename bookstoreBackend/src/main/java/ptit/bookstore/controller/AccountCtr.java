package ptit.bookstore.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptit.bookstore.model.Staff;
import ptit.bookstore.model.User;
import ptit.bookstore.service.StaffService;
import ptit.bookstore.utility.BreadcumInfo;
import ptit.bookstore.utility.CheckSession;

@Controller
public class AccountCtr {
	@Autowired
	private StaffService staffService;

	@RequestMapping("/account/index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/account/index");
		
		//check session
		if (!CheckSession.checkUserSession(session))
			return new ModelAndView("redirect:/admin_login");	
		User user = (User) session.getAttribute("user");		
		mav.addObject("user", user);
		
		List<Staff> listStaff = staffService.findAll();
		mav.addObject("listStaff", listStaff);
				
		// breadcum
		List<BreadcumInfo> listBreadCum = new ArrayList<BreadcumInfo>();
		BreadcumInfo homepage = new BreadcumInfo("Home", "/bookstore/index", false);
		BreadcumInfo bookPage = new BreadcumInfo("Account", "/bookstore/account/index", false);
		BreadcumInfo current = new BreadcumInfo("Index", "#", true);
		listBreadCum.add(homepage);
		listBreadCum.add(bookPage);
		listBreadCum.add(current);
		mav.addObject("listBreadCum", listBreadCum);

		return mav;
	}

	@RequestMapping("/account/add")
	ModelAndView add() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/account/add");

		// breadcum
		List<BreadcumInfo> listBreadCum = new ArrayList<BreadcumInfo>();
		BreadcumInfo homepage = new BreadcumInfo("Home", "/bookstore/index", false);
		BreadcumInfo bookPage = new BreadcumInfo("Account", "/bookstore/account/index", false);
		BreadcumInfo current = new BreadcumInfo("Add", "#", true);
		listBreadCum.add(homepage);
		listBreadCum.add(bookPage);
		listBreadCum.add(current);
		mav.addObject("listBreadCum", listBreadCum);
		return mav;
	}
	
	
	@RequestMapping(value = "/account/add", method = RequestMethod.POST)
	ModelAndView doAdd(@ModelAttribute Staff staff, RedirectAttributes redirect) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/account/index");
		staff.setRole("Staff");		
		boolean flag = staffService.save(staff);
		if (flag) {
			redirect.addFlashAttribute("succsessMsg", "Add Success");
		}
		else
			redirect.addFlashAttribute("errorMsg", "Add fail");			
		return mav;
	}
	
	@RequestMapping(value = "/account/delete/{id}", method = RequestMethod.GET)
	ModelAndView doAdd(@PathVariable("id") int id, RedirectAttributes redirect) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/account/index");
		boolean flag = staffService.delete(id);
		if (flag)
			redirect.addFlashAttribute("successMsg", "Delete success");
		else
			redirect.addFlashAttribute("errorMsg", "Delete fail");
		return mav;
	}

}
