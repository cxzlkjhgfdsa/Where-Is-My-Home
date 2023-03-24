package com.ssafy.member.dto;

public class LoginSessionModel {

	String id, pw, name;
	boolean isOnSession;

	public LoginSessionModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginSessionModel(String id, String pw, String name, boolean isOnSession) {
		setId(id);
		setPw(pw);
		setName(name);
		setId(id);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPw() {
		return pw;
	}

	public void setPw(String pw) {
		this.pw = pw;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isOnSession() {
		return isOnSession;
	}

	public void setOnSession(boolean isOnSession) {
		this.isOnSession = isOnSession;
	}

	@Override
	public String toString() {
		return "LoginSessionModel [id=" + id + ", pw=" + pw + ", name=" + name + ", isOnSession=" + isOnSession + "]";
	}

}
