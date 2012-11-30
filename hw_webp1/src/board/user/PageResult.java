package board.user;

import java.util.*;

// <T> - 선언시 Type이 들어감 ex) new result(type);
public class PageResult<T> implements java.io.Serializable{
	private static final long serialVersionUID = -1826830567659349558L;
	
	private List<T> list;
	private int numItemsInPage;
	private int numItems;
	private int numPages;
	private int page;
	
	private final static int delta = 10; // 상수 한페이지에 보여줄 갯수
	public List<T> getListUsers(){
		return list;
	}
	
	//생성자
	public PageResult(int numItemsInPage, int page) {
		super();
		this.numItemsInPage = numItemsInPage;
		this.page = page;
		numItems = 0;
		numPages = 0;
		list = new ArrayList<T>();
	}
	
	public List<T> getList() {
		return list;
	}
	
	public int getNumItemsInPage() {
		return numItemsInPage;
	}

	public int getNumItems() {
		return numItems;
	}
	
	public void setNumItems(int numItems) {
		this.numItems = numItems;
		numPages = (int) Math.ceil((double)numItems / (double)numItemsInPage);
	}
	
	public int getNumPages() {
		return numPages;
	}
	
	public int getPage() {
		return page;
	}
	
	public int getStartPageNo() {
		return (page <= delta) ? 1: page - delta;
	}
	
	public int getEndPageNo() {
		int endPageNo = getStartPageNo() + (delta * 2) + 1;

		if (endPageNo > numPages) {
			endPageNo = numPages;
		}

		return endPageNo;
	}
}
