package ptit.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ptit.bookstore.dao.CategoryDao;
import ptit.bookstore.model.Category;

@Service
public class CategoryService {
	@Autowired
	private CategoryDao categoryDao;
	
	public List<Category> findAll(){
		return categoryDao.findAll();
	}
	
	public Category findOne(int id) {
		return categoryDao.findOne(id);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Category update(Category category) {
		return categoryDao.update(category);
	}
	
	@Transactional(rollbackFor = Exception.class)
	public Category save(Category category) {
		category = categoryDao.save(category);
		if (category.getId() == 0)
			return new Category();
		return category;
	}
	
	public boolean delete(int id) {
		return categoryDao.delete(id);
	}
}
