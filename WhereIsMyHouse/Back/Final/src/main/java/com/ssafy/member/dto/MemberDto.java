package com.ssafy.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "MemberDto : 회원정보", description = "회원의 상세 정보를 나타낸다.")
public class MemberDto {

	private int no;
	private String id;
	private String nickname;
	private String password;
	private int toggle;
	private String token;
	
	
	public MemberDto() {
		super();
		// TODO Auto-generated constructor stub
	}


	public MemberDto(int no, String id, String nickname, String password, int toggle, String token) {
		super();
		this.no = no;
		this.id = id;
		this.nickname = nickname;
		this.password = password;
		this.toggle = toggle;
		this.token = token;
	}


	public int getNo() {
		return no;
	}


	public void setNo(int no) {
		this.no = no;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getNickname() {
		return nickname;
	}


	public void setNickname(String nickname) {
		this.nickname = nickname;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public int getToggle() {
		return toggle;
	}


	public void setToggle(int toggle) {
		this.toggle = toggle;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	@Override
	public String toString() {
		return "MemberDto [no=" + no + ", id=" + id + ", nickname=" + nickname + ", password=" + password + ", toggle="
				+ toggle + ", token=" + token + "]";
	}
	
	



}
