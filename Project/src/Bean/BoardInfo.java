package Bean;

public class BoardInfo implements java.io.Serializable {

	static final long serialVersionUID = -3043816429450037587L;
	
	private int b_number;
	private int groupnumber;
	private String b_content;
	private String b_id;
	private String b_pwd;
	
	public BoardInfo(){}
	
	public BoardInfo(int groupnumber ,String b_content ,String b_id,String b_pwd){
		this.groupnumber=groupnumber;
		this.b_content=b_content;
		this.b_id=b_id;
		this.b_pwd=b_pwd;
	}
	
	public int getB_number() {
		return b_number;
	}
	public void setB_number(int b_number) {
		this.b_number = b_number;
	}
	public int getGroupnumber() {
		return groupnumber;
	}
	public void setGroupnumber(int groupnumber) {
		this.groupnumber = groupnumber;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public String getB_id() {
		return b_id;
	}

	public void setB_id(String b_id) {
		this.b_id = b_id;
	}

	public String getB_pwd() {
		return b_pwd;
	}
	public void setB_pwd(String b_pwd) {
		this.b_pwd = b_pwd;
	}
	
	
	
}
