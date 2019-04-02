package ptit.bookstore.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptit.bookstore.model.Author;
import ptit.bookstore.model.Book;
import ptit.bookstore.model.Category;
import ptit.bookstore.model.Publisher;
import ptit.bookstore.service.AuthorService;
import ptit.bookstore.service.BookService;
import ptit.bookstore.service.CategoryService;
import ptit.bookstore.service.PublisherService;
import ptit.bookstore.utility.AppPram;

@Controller
public class BookCtr {
	@Autowired
	private PublisherService publisherService;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private BookService bookService;
	
	@RequestMapping("/book/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/book/index");
		return mav;
	}

	@RequestMapping("/book/add")
	public ModelAndView add() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("/book/add");
		List<Publisher> listPublisher = publisherService.findAll();
		List<Category> listCategory = categoryService.findAll();
		List<Author> listAuthor = authorService.findAll();
		mav.addObject("listPublisher", listPublisher);
		mav.addObject("listCategory", listCategory);
		mav.addObject("listAuthor", listAuthor);
		return mav;
	}

	@RequestMapping(value = "/book/add", method = RequestMethod.POST)
	public ModelAndView doAdd(@ModelAttribute Book book, BindingResult result, @RequestParam("catList") ArrayList<Integer> listCatId,
			@RequestParam("imageFile") MultipartFile multipartFile, RedirectAttributes redirect) {
		ModelAndView mav = new ModelAndView();
		
		if (result.hasErrors()) {			
			redirect.addFlashAttribute("errorMsg", "Error converting data at field " + result.getFieldError().getField());
			return new ModelAndView("redirect:/book/add");
		}
		
		List<Category> listCategory = new ArrayList<Category>();
		for (Integer id :listCatId) {
			Category temp = new Category();
			temp.setId(id);
			listCategory.add(temp);
		}
		book.setCategory(listCategory);		
		String fileName = multipartFile.getOriginalFilename();		
		book.setImgUrl(fileName);		
		if (multipartFile.getOriginalFilename().equals("") || multipartFile.getSize() == 0) {
			redirect.addFlashAttribute("errorMsg", "File is empty or name of file is blank");
			return new ModelAndView("redirect:/book/add");
		}
		try {
			Path path = Paths.get(AppPram.uploadDir + multipartFile.getOriginalFilename());				
			Files.write(path, multipartFile.getBytes());			
		} catch (IOException e) {			
			e.printStackTrace();
			redirect.addFlashAttribute("errorMsg", "Upload fail, please try again");
			return new ModelAndView("redirect:/book/add");
		}				
		bookService.save(book);
		redirect.addFlashAttribute("successMsg", "Add successful");
		mav.setViewName("redirect:/book/index");
		return mav;
	}

	@RequestMapping(value = "/book/image", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
	public @ResponseBody byte[] getImage(@RequestParam String fileName) throws IOException {				
		File file = new File(AppPram.uploadDir + File.separator + fileName);
		InputStream in = null;
		try {
			in = new FileInputStream(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
		byte[] image = IOUtils.toByteArray(in);		
		in.close();
		return image;
	}

}
