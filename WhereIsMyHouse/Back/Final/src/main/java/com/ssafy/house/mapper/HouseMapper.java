package com.ssafy.house.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.dto.DealLog;
import com.ssafy.house.dto.DongCode;
import com.ssafy.house.dto.HouseInfo;
import com.ssafy.house.dto.School;

@Mapper
public interface HouseMapper {

	List<DongCode> getSido();

	List<DongCode> getGugun(Map<String, String> sidoCode);

	List<DongCode> getDong(Map<String, String> gugunCode);

	List<HouseInfo> getHouseList(Map<String, Object> item);

	int checkFavorite(Map<String, String> item);

	int addFavorite(Map<String, String> item);

	List<HouseInfo> getFavorite( Map<String, String> user);

	HouseInfo getAptInfo(Map<String, String> code);

	List<School> getSchool(HouseInfo houseinfo);

	int deleteFavorite(Map<String, String> item);

	List<DealLog> getDealLog(Map<String, String> aptCode);

	List<DealLog> getAvgDong(Map<String, String> dongCode);

}
