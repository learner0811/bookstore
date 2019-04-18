package ptit.bookstore.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptit.bookstore.model.Category;
import ptit.bookstore.service.CategoryService;
import ptit.bookstore.utility.AppPram;

@Controller
public class CategoryCtr {	
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/category/index")
	ModelAndView index(@RequestParam(value="page" ,required=false) Integer pageNumber) {
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("/category/index");
		List<Category> listCat = categoryService.findAll();
		mav.addObject("listCategory", listCat);
		
		//paging
		//neu tong so ban ghi chia het cho so ban ghi mot trang
		//tong so trang = tong so bang ghi chia cho so ban ghi mot trang
		//nguoc lai = tong so bang ghi chia cho so ban ghi mot trang lam tron len
		int totalPage = (int) (listCat.size() % 5 == 0 ? listCat.size()/AppPram.RECORD_PER_ROW : Math.ceil((double)listCat.size()/AppPram.RECORD_PER_ROW));
		
		if (pageNumber != null && pageNumber > 0) {
			mav.addObject("page", pageNumber);
			mav.addObject("startIndex", pageNumber*AppPram.RECORD_PER_ROW-AppPram.RECORD_PER_ROW);
			mav.addObject("endIndex", pageNumber*AppPram.RECORD_PER_ROW-1);
			mav.addObject("totalPage", totalPage);
		}else {
			mav.addObject("page", 1);
			mav.addObject("startIndex",0);
			mav.addObject("endIndex", 4);
			mav.addObject("totalPage", totalPage);
		}
		return mav;
	}	
	
	@RequestMapping("/category/add")
	ModelAndView add() {
		ModelAndView mav = new ModelAndView();		
		mav.setViewName("/category/add");
		return mav;
	}
	
	@RequestMapping(value = "/category/add", method = RequestMethod.POST)
	ModelAndView doAdd(@ModelAttribute Category category, BindingResult bind, RedirectAttributes redirect) {		
		ModelAndView mav = new ModelAndView();
		if (bind.hasErrors()) {
			redirect.addFlashAttribute("errorMsg", "your input is invalid format");
			mav.setViewName("redirect:/category/add");
			return mav;
		}
		category = categoryService.save(category);	
		if (category == null) {
			redirect.addFlashAttribute("errorMsg", "Category name may exist");
			mav.setViewName("redirect:/category/add");
			return mav;
		}
		redirect.addFlashAttribute("successMsg", "add success");
		mav.setViewName("redirect:/category/index");
		return mav;
	}
	
	@RequestMapping("/category/edit/{id}")
	ModelAndView edit(@PathVariable int id) {
		ModelAndView mav = new ModelAndView();				
		mav.setViewName("/category/edit");		
		Category category = categoryService.findOne(id);		
		//if (category == null)
			//xu ly loi
			
		//else {
			
		//}		
		mav.addObject("category", category);
		return mav;
	}
	
	@RequestMapping(value = "/category/edit", method = RequestMethod.POST)
	ModelAndView doEdit(@ModelAttribute Category category) {
		ModelAndView mav = new ModelAndView();				
		category = categoryService.update(category);
		//if (category == null) xu ly loi
		mav.setViewName("redirect:/category/index");
		return mav;
	}
	
	
	@RequestMapping(value = "/category/delete/{id}")
	ModelAndView delete(@PathVariable int id) {
		ModelAndView mav = new ModelAndView();				
		boolean result  = categoryService.delete(id);
		System.out.println("delete operation is " + result);
		//if (result == false) xu ly loi
		mav.setViewName("redirect:/category/index");
		return mav;
	}
}
