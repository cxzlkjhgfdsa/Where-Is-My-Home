package com.ssafy.member.dto;

public class SecurityModel {

	private int loginFailedCount, retry;
	private long lastFailedLogin;
	private long lastSuccessedLogin;
	private String id, clientIp;

	public SecurityModel() {
		super();
	}

	public SecurityModel(int loginFailedCount, int retry, long lastFailedLogin, long lastSuccessedLogin, String id,
			String clientIp) {
		super();
		this.loginFailedCount = loginFailedCount;
		this.retry = retry;
		this.lastFailedLogin = lastFailedLogin;
		this.lastSuccessedLogin = lastSuccessedLogin;
		this.id = id;
		this.clientIp = clientIp;
	}

	public int getLoginFailedCount() {
		return loginFailedCount;
	}

	public void setLoginFailedCount(int loginFailedCount) {
		this.loginFailedCount = loginFailedCount;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

	public long getLastFailedLogin() {
		return lastFailedLogin;
	}

	public void setLastFailedLogin(long lastFailedLogin) {
		this.lastFailedLogin = lastFailedLogin;
	}

	public long getLastSuccessedLogin() {
		return lastSuccessedLogin;
	}

	public void setLastSuccessedLogin(long lastSuccessedLogin) {
		this.lastSuccessedLogin = lastSuccessedLogin;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClientIp() {
		return clientIp;
	}

	public void setClientIp(String clientIp) {
		this.clientIp = clientIp;
	}

	@Override
	public String toString() {
		return "SecurityModel [loginFailedCount=" + loginFailedCount + ", retry=" + retry + ", lastFailedLogin="
				+ lastFailedLogin + ", lastSuccessedLogin=" + lastSuccessedLogin + ", Id=" + id + ", clientIp="
				+ clientIp + "]";
	}

}
