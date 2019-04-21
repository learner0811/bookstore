package ptit.bookstore.utility;

public class BreadcumInfo {
	private String name;
	private String url;
	private boolean current = false;
	public BreadcumInfo() {
		
	}
		
	public BreadcumInfo(String name, String url, boolean isCurrent) {		
		this.name = name;
		this.url = url;
		this.current = isCurrent;
	}

	

	public boolean isCurrent() {
		return current;
	}

	public void setCurrent(boolean isCurrent) {
		this.current = isCurrent;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
