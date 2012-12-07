package Bean;

import json.*;
import crystars.*;

public class UserDataObj implements java.io.Serializable {
	private static final long serialVersionUID = 1922283819;
	
	public UserDataObj()
	{
		
	}
	
	private String id;
	private String token;
	private String portrait;
	public String getPortrait() {
		return portrait;
	}

	public void setPortrait(String portrait) {
		this.portrait = portrait;
	}

	private String name;
	private int grade;
	private String email;
	private String home;
	
	public String getJsonString(){
		String rtv="";
		JSONObject obj = null;
		try{
		obj = new JSONObject();
        obj.put("id", id);
        obj.put("name", name);
        obj.put("grade", grade);
        obj.put("email", email);
        obj.put("home", home);
        obj.put("token", token);
        obj.put("portrait", portrait);
        rtv = obj.toString();
		}catch(Exception e){
		rtv = "{\"error\":"+ERROR.USER_JSON_BIND_ERROR+"}"; 
		}
        
		return rtv;
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

	public void initial(String _id,String _name,String _email,String _home,String _portrait,String _token){
		

		id = _id;
		name = _name;
		grade =1;
		email=_email;
		home=_home;
		portrait = _portrait;
		token = _token;
		
		if(id==null)
			id="";
		
		if(name==null)
			name="";
		
		if(email==null)
			email="";
		
		if(home==null)
			home="";
		
		if(portrait==null)
			portrait="";
		
		if(token==null)
			token="";
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public int getGrade() {
		return grade;
	}
	public void setGrade(int auth) {
		this.grade = auth;
	}
	
}
