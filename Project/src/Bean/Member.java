package Bean;

public class Member implements java.io.Serializable{

	static final long serialVersionUID = 2518958318856997860L;
	
	private String Member_Num;
	private String Facebook_ID;
	private String Nickname;
	private int Grade;
	private String email;
	private String home;
	
	public String getMember_Num() {
		return Member_Num;
	}
	public void setMember_Num(String member_Num) {
		Member_Num = member_Num;
	}
	public String getFacebook_ID() {
		return Facebook_ID;
	}
	public void setFacebook_ID(String facebook_ID) {
		Facebook_ID = facebook_ID;
	}
	public String getNickname() {
		return Nickname;
	}
	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	public int getGrade() {
		return Grade;
	}
	public void setGrade(int grade) {
		Grade = grade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getHome() {
		return home;
	}
	public void setHome(String home) {
		this.home = home;
	}
	
}
