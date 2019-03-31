package ptit.bookstore.model;

import java.util.List;

public class Cart {
	private int id;
	private List<Book> listBook;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Book> getListBook() {
		return listBook;
	}
	public void setListBook(List<Book> listBook) {
		this.listBook = listBook;
	}
	
	
}
