package ptit.bookstore.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptit.bookstore.model.Book;
import ptit.bookstore.model.Order;
import ptit.bookstore.model.User;
import ptit.bookstore.service.OrderService;
import ptit.bookstore.utility.AppPram;
import ptit.bookstore.utility.BreadcumInfo;
import ptit.bookstore.utility.CheckSession;

@Controller
public class ProcessOrderCtr {
	@Autowired
	private OrderService orderService;

	@RequestMapping("/order/index")
	public ModelAndView mav(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/process_order/index");

		// check session
		if (!CheckSession.checkUserSession(session))
			return new ModelAndView("redirect:/admin_login");
		User user = (User) session.getAttribute("user");
		mav.addObject("user", user);

		List<Order> listOrder = orderService.getListOrder();
		mav.addObject("listOrder", listOrder);

		// breadcum
		List<BreadcumInfo> listBreadCum = new ArrayList<BreadcumInfo>();
		BreadcumInfo homepage = new BreadcumInfo("Home", "/bookstore/index", false);
		BreadcumInfo bookPage = new BreadcumInfo("Order", "/bookstore/order/index", false);
		BreadcumInfo current = new BreadcumInfo("Index", "#", true);
		listBreadCum.add(homepage);
		listBreadCum.add(bookPage);
		listBreadCum.add(current);
		mav.addObject("listBreadCum", listBreadCum);
		return mav;
	}

	@RequestMapping("/order/changeStatus")
	public String changeStatus(@RequestParam int orderId, RedirectAttributes redirect) {
		System.out.println("orderid is " + orderId);
		if (orderId == 0)
			redirect.addFlashAttribute("successMsg", "Change fail");
		else {
			orderService.ChangeStatus(orderId);
			redirect.addFlashAttribute("successMsg", "Change success");
		}
		return "redirect:/order/index";
	}

	@RequestMapping("/get_list_order_json")
	@ResponseBody
	public List<Order> getListOrder() {
		List<Order> listOrder = orderService.getListOrder();
		return listOrder;
	}

	@RequestMapping("/get_listbook_byorderid/{id}")
	@ResponseBody
	public List<Book> getListBookByOrderId(@PathVariable int id) {
		List<Book> listBook = orderService.getListBookByOrder(id);
		return listBook;
	}
}
