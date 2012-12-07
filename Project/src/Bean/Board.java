package Bean;

public class Board implements java.io.Serializable {

	private static final long serialVersionUID = 7314105566850625262L;
	
	private int number;
	private String title;
	private String content;
	private String userid;
	private String pwd;
	private int count;
	private String file;

	public Board(){}
	
	public Board(int number,String title,String content, String userid,String pwd,int count,String file){
		this.number = number;
		this.title = title;
		this.content = content;
		this.userid = userid;
		this.pwd = pwd;
		this.count = count;
		this.file=file;
	}
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getFile() {
		return file;
	}
	public void setFile(String file) {
		this.file = file;
	}
	
}
