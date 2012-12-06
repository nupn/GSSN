package com.crystars.user;



public class user{
	private int mem_num;
	private String facebooknum;
	private String nickname;
	private String facebookprofileimg;
	private String home;
	private String email;

	
	public user(){}; // 생성자
	
	public user(int mem_num, String facebooknum, String nickname, String facebookprofileimg,String home, String email){
		super();
		this.mem_num = mem_num;
		this.facebooknum = facebooknum;
		this.nickname = nickname;
		this.facebookprofileimg =facebookprofileimg;
		this.home = home;
		this.email = email;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home)  {
		this.home =home;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public int getMem_num() {
		return mem_num;
	}

	public void setMem_num(int mem_num) {
		this.mem_num = mem_num;
	}

	public String getFacebooknum() {
		return facebooknum;
	}

	public void setFacebooknum(String facebooknum){
		this.facebooknum =facebooknum;
	}

	

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getFacebookprofileimg() {
		return facebookprofileimg;
	}

	public void setFacebookprofileimg(String facebookprofileimg) {
		this.facebookprofileimg = facebookprofileimg;
	}
	


	
	
}
