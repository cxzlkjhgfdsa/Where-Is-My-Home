package com.ssafy.board.dto;

import java.sql.Date;

public class Board {
	private int code, hits, favCount;
	private String title, content, nickname;
	private String reg_datetime;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getReg_datetime() {
		return reg_datetime;
	}
	public void setReg_datetime(String reg_datetime) {
		this.reg_datetime = reg_datetime;
	}
	
	public int getFavCount() {
		return favCount;
	}
	public void setFavCount(int favCount) {
		this.favCount = favCount;
	}
	@Override
	public String toString() {
		return "Board [code=" + code + ", hits=" + hits + ", favCount=" + favCount + ", title=" + title + ", content="
				+ content + ", nickname=" + nickname + ", reg_datetime=" + reg_datetime + "]";
	}
	
	
	

}
