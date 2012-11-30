package board.user;

import java.sql.Timestamp;

public class boardinfo {
	private int	num;
	private String title;
	private String contents;
	private Timestamp date; //rs.getTimestamp(...);
	private String name;
	private int count;
	private String pwd;
	
	public boardinfo(){}; // 생성자
	
	public boardinfo(int num, String title, String contents, Timestamp date, String name, int count, String pwd){
		
		super();
		this.num =num;
		this.title = title;
		this.contents = contents;
		this.date = date;
		this.name = name;
		this.count = count;
		this.pwd = pwd;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
