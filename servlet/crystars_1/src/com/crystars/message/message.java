package com.crystars.message;

import java.sql.Timestamp;

public class message{
	private int msg_num;
	private int to_memnum;
	private int from_memnum;
	private String title;
	private String content;
	private Timestamp to_date;//rs.getTimestamp(...);
	private boolean yesno;
	private String Nickname;
	private String myNickname;	
	private String otherNickname;

	
	public message(){}; // 생성자
	
	public message(String myNickname, String otherNickname, String title, String content,Timestamp to_date, int to_memnum, int from_memnum, boolean yesno){
		super();
		this.otherNickname = otherNickname;
		this.myNickname = myNickname;
		this.to_memnum = to_memnum;
		this.from_memnum = from_memnum;
		this.title = title;
		this.content = content;
		this.to_date = to_date;
		this.yesno = yesno;
	}
	
	
	
	public message(String Nickname, int msg_num, int to_memnum, int from_memnum, String title, String content, Timestamp to_date, boolean yesno){
		super();
		this.Nickname = Nickname;
		this.msg_num=msg_num;
		this.to_memnum=to_memnum;
		this.from_memnum = from_memnum;
		this.title = title;
		this.content = content;
		this.to_date = to_date;
		this.yesno = yesno;
	}

	public int getTo_memnum() {
		return to_memnum;
	}

	public void setTo_memnum(int to_memnum) {
		this.to_memnum = to_memnum;
	}

	public int getFrom_memnum() {
		return from_memnum;
	}

	public void setFrom_memnum(int from_memnum) {
		this.from_memnum = from_memnum;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getTo_date() {
		return to_date;
	}

	public void setTo_date(Timestamp to_date) {
		this.to_date = to_date;
	}

	public boolean isYesno() {
		return yesno;
	}

	public void setYesno(boolean yesno) {
		this.yesno = yesno;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public String getMyNickname() {
		return myNickname;
	}

	public void setMyNickname(String myNickname) {
		this.myNickname = myNickname;
	}

	public String getOtherNickname() {
		return otherNickname;
	}

	public void setOtherNickname(String otherNickname) {
		this.otherNickname = otherNickname;
	}


	
	
}
