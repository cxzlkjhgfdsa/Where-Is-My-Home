package com.ssafy.board.dto;

public class Comment {
	int no, code;
	String comment, nickname;
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	@Override
	public String toString() {
		return "Comment [no=" + no + ", code=" + code + ", comment=" + comment + ", nickname=" + nickname + "]";
	}
	
	

}
