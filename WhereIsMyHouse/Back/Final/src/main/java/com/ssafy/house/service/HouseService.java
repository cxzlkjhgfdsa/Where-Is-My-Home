package com.ssafy.house.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.house.dto.DealLog;
import com.ssafy.house.dto.DongCode;
import com.ssafy.house.dto.HouseInfo;
import com.ssafy.house.dto.School;
import com.ssafy.house.mapper.HouseMapper;

@Service
public class HouseService {
	
	@Autowired
	HouseMapper houseMapper;

	public List<DongCode> getSido() {
		// TODO Auto-generated method stub
		return houseMapper.getSido();
	}

	public List<DongCode> getGugun(Map<String, String> sidoCode) {
		// TODO Auto-generated method stub
		return houseMapper.getGugun(sidoCode);
	}

	public List<DongCode> getDong(Map<String, String> gugunCode) {
		// TODO Auto-generated method stub
		return houseMapper.getDong(gugunCode);
	}

	public List<HouseInfo> getHouseList(Map<String, Object> item) {
		// TODO Auto-generated method stub
		return houseMapper.getHouseList(item);
	}

	public int checkFavorite(Map<String, String> item) {
		// TODO Auto-generated method stub
		return houseMapper.checkFavorite(item);
	}

	public int addFavorite(Map<String, String> item) {
		// TODO Auto-generated method stub
		return houseMapper.addFavorite(item);
	}

	public List<HouseInfo> getFavorite( Map<String, String> user) {
		// TODO Auto-generated method stub
		return houseMapper.getFavorite(user);
	}

	public HouseInfo getAptInfo(Map<String, String> code) {
		// TODO Auto-generated method stub
		return houseMapper.getAptInfo(code);
	}

	public List<School> getSchool(HouseInfo houseinfo) {
		// TODO Auto-generated method stub
		return houseMapper.getSchool(houseinfo);
	}

	public int deleteFavorite(Map<String, String> item) {
		// TODO Auto-generated method stub
		return houseMapper.deleteFavorite(item);
	}

	public List<DealLog> getDealLog(Map<String, String> aptCode) {
		// TODO Auto-generated method stub
		return houseMapper.getDealLog(aptCode);
	}

	public List<DealLog> getAvgDong(Map<String, String> dongCode) {
		// TODO Auto-generated method stub
		return houseMapper.getAvgDong(dongCode);
	}

}
