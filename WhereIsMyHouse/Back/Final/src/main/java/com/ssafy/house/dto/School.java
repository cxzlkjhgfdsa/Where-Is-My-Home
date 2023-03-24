package com.ssafy.house.dto;

public class School {
	private String schoolid, schoolname, schoolVal, buildshape, bonbun, roadname, lat, lng, buildday;

	public String getSchoolid() {
		return schoolid;
	}

	public void setSchoolid(String schoolid) {
		this.schoolid = schoolid;
	}

	public String getSchoolname() {
		return schoolname;
	}

	public void setSchoolname(String schoolname) {
		this.schoolname = schoolname;
	}

	public String getSchoolVal() {
		return schoolVal;
	}

	public void setSchoolVal(String schoolVal) {
		this.schoolVal = schoolVal;
	}

	public String getBuildshape() {
		return buildshape;
	}

	public void setBuildshape(String buildshape) {
		this.buildshape = buildshape;
	}

	public String getBonbun() {
		return bonbun;
	}

	public void setBonbun(String bonbun) {
		this.bonbun = bonbun;
	}

	public String getRoadname() {
		return roadname;
	}

	public void setRoadname(String roadname) {
		this.roadname = roadname;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLng() {
		return lng;
	}

	public void setLng(String lng) {
		this.lng = lng;
	}

	public String getBuildday() {
		return buildday;
	}

	public void setBuildday(String buildday) {
		this.buildday = buildday;
	}

	@Override
	public String toString() {
		return "School [schoolid=" + schoolid + ", schoolname=" + schoolname + ", schoolVal=" + schoolVal
				+ ", buildshape=" + buildshape + ", bonbun=" + bonbun + ", roadname=" + roadname + ", lat=" + lat
				+ ", lng=" + lng + ", buildday=" + buildday + "]";
	}
	
	

}
