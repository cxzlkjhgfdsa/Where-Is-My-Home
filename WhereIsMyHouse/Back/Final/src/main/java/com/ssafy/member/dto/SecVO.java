package com.ssafy.member.dto;

public class SecVO {
	private String id;
	private String salt;
	private String secKey;

	public SecVO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SecVO(String id, String salt, String secKey) {
		super();
		this.id = id;
		this.salt = salt;
		this.secKey = secKey;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getSecKey() {
		return secKey;
	}

	public void setSecKey(String secKey) {
		this.secKey = secKey;
	}

	@Override
	public String toString() {
		return "SecVO [id=" + id + ", salt=" + salt + ", secKey=" + secKey + "]";
	}


}
