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

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ptit.bookstore.model.Author;
import ptit.bookstore.model.BookInfo;
import ptit.bookstore.model.Category;
import ptit.bookstore.model.Publisher;
import ptit.bookstore.model.User;
import ptit.bookstore.service.AuthorService;
import ptit.bookstore.service.BookService;
import ptit.bookstore.service.CategoryService;
import ptit.bookstore.service.PublisherService;
import ptit.bookstore.utility.AppPram;
import ptit.bookstore.utility.BreadcumInfo;
import ptit.bookstore.utility.CheckSession;

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
	public ModelAndView index(@RequestParam(value = "page", required = false) Integer pageNumber, HttpSession session) {
		ModelAndView mav = new ModelAndView();
		List<BookInfo> listBook = bookService.getAllBook();
		mav.addObject("listBook", listBook);
		mav.setViewName("/book/index");

		// check session
		if (!CheckSession.checkUserSession(session))
			return new ModelAndView("redirect:/admin_login");
		User user = (User) session.getAttribute("user");
		mav.addObject("user", user);

		int totalPage = (int) (listBook.size() % 5 == 0 ? listBook.size() / AppPram.RECORD_PER_ROW
				: Math.ceil((double) listBook.size() / AppPram.RECORD_PER_ROW));

		if (pageNumber != null && pageNumber > 0) {
			mav.addObject("page", pageNumber);
			mav.addObject("startIndex", pageNumber * AppPram.RECORD_PER_ROW - AppPram.RECORD_PER_ROW);
			mav.addObject("endIndex", pageNumber * AppPram.RECORD_PER_ROW - 1);
			mav.addObject("totalPage", totalPage);
		} else {
			mav.addObject("page", 1);
			mav.addObject("startIndex", 0);
			mav.addObject("endIndex", 4);
			mav.addObject("totalPage", totalPage);
		}

		// breadcum
		List<BreadcumInfo> listBreadCum = new ArrayList<BreadcumInfo>();
		BreadcumInfo homepage = new BreadcumInfo("Home", "/bookstore/index", false);
		BreadcumInfo book = new BreadcumInfo("Book", "/bookstore/book/index", false);
		BreadcumInfo current = new BreadcumInfo("index", "#", true);
		listBreadCum.add(homepage);
		listBreadCum.add(book);
		listBreadCum.add(current);
		mav.addObject("listBreadCum", listBreadCum);

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

		// breadcum
		List<BreadcumInfo> listBreadCum = new ArrayList<BreadcumInfo>();
		BreadcumInfo homepage = new BreadcumInfo("Home", "/bookstore/index", false);
		BreadcumInfo book = new BreadcumInfo("Book", "/bookstore/book/index", false);
		BreadcumInfo current = new BreadcumInfo("Add", "#", true);
		listBreadCum.add(homepage);
		listBreadCum.add(book);
		listBreadCum.add(current);
		mav.addObject("listBreadCum", listBreadCum);

		return mav;
	}

	@RequestMapping(value = "/book/add", method = RequestMethod.POST)
	public ModelAndView doAdd(@ModelAttribute BookInfo book, BindingResult result,
			@RequestParam("catList") ArrayList<Integer> listCatId,
			@RequestParam("imageFile") MultipartFile multipartFile, RedirectAttributes redirect) {
		ModelAndView mav = new ModelAndView();

		if (result.hasErrors()) {
			redirect.addFlashAttribute("errorMsg",
					"Error converting data at field " + result.getFieldError().getField());
			return new ModelAndView("redirect:/book/add");
		}

		List<Category> listCategory = new ArrayList<Category>();
		for (Integer id : listCatId) {
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

	@RequestMapping(value = "/book/edit/{id}")
	public ModelAndView edit(@PathVariable int id, RedirectAttributes redirect) {
		ModelAndView mav = new ModelAndView();

		List<Publisher> listPublisher = publisherService.findAll();
		List<Category> listCategory = categoryService.findAll();
		List<Author> listAuthor = authorService.findAll();
		mav.addObject("listPublisher", listPublisher);
		mav.addObject("listCategory", listCategory);
		mav.addObject("listAuthor", listAuthor);

		BookInfo book = bookService.getBookById(id);
		ArrayList<Integer> listCatId = new ArrayList<Integer>();
		List<Category> listCat = book.getCategory();
		for (int i = 0; i < listCat.size(); i++)
			listCatId.add(listCat.get(i).getId());
		/*
		 * String path = AppPram.uploadDir + book.getImgUrl(); //MultipartFile mul try {
		 * 
		 * } catch (Exception ex) { ex.printStackTrace(); }
		 */
		mav.addObject("book", book);
		mav.addObject("listCatId", listCatId);
		mav.setViewName("/book/edit");

		// breadcum
		List<BreadcumInfo> listBreadCum = new ArrayList<BreadcumInfo>();
		BreadcumInfo homepage = new BreadcumInfo("Home", "/bookstore/index", false);
		BreadcumInfo bookPage = new BreadcumInfo("Book", "/bookstore/book/index", false);
		BreadcumInfo current = new BreadcumInfo("Edit", "#", true);
		listBreadCum.add(homepage);
		listBreadCum.add(bookPage);
		listBreadCum.add(current);
		mav.addObject("listBreadCum", listBreadCum);

		return mav;
	}

	@RequestMapping(value = "/book/edit", method = RequestMethod.POST)
	public ModelAndView doEdit(@ModelAttribute BookInfo book, BindingResult result,
			@RequestParam("listCat") List<Integer> listCatId, @RequestParam("imageFile") MultipartFile multipartFile,
			RedirectAttributes redirect) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/book/index");
		if (result.hasErrors()) {
			System.out.println("field error is " + result.getFieldError().getField());
		}
		List<Category> listCategory = new ArrayList<Category>();
		for (Integer id : listCatId) {
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

		// update book
		boolean flag = bookService.update(book);
		if (flag == true) {
			redirect.addFlashAttribute("successMsg", "update success");
		} else {
			redirect.addFlashAttribute("erroMsg", "update success");
		}

		return mav;
	}

	@RequestMapping(value = "/book/delete/{id}")
	public ModelAndView delete(@PathVariable int id, RedirectAttributes redirect) {
		ModelAndView mav = new ModelAndView();
		bookService.delete(id);
		redirect.addFlashAttribute("successMsg", "delete Success");
		mav.setViewName("redirect:/book/index");
		return mav;
	}

	@RequestMapping(value = "/book/updateQuantity")
	public ModelAndView updateQuantity(@RequestParam int id, @RequestParam int quantity, RedirectAttributes redirect) {
		ModelAndView mav = new ModelAndView();
		System.out.println("id " + id);
		System.out.println("quantity " + quantity);
		BookInfo book = bookService.getBookById(id);
		String msg = bookService.updateQuantity(book, quantity);
		redirect.addFlashAttribute("successMsg", msg);
		mav.setViewName("redirect:/book/index");
		return mav;
	}

	@RequestMapping(value = "/book/image", produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
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
