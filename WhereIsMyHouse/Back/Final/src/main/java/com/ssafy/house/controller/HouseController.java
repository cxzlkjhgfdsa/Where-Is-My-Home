package com.ssafy.house.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssafy.house.dto.DealLog;
import com.ssafy.house.dto.DongCode;
import com.ssafy.house.dto.HouseInfo;
import com.ssafy.house.dto.School;
import com.ssafy.house.service.HouseService;

@Controller
@RequestMapping("/house")
public class HouseController {

	@Autowired
	HouseService houseService;

	@PostMapping
	@RequestMapping("/getSido")
	public ResponseEntity<List<DongCode>> getSido() {

		List<DongCode> sidoList = houseService.getSido();
		// System.out.println(sidoList);

		return new ResponseEntity<List<DongCode>>(sidoList, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/getGugun")
	public ResponseEntity<List<DongCode>> getGugun(@RequestBody Map<String, String> sidoCode) {

		List<DongCode> gugunList = houseService.getGugun(sidoCode);
		// System.out.println(sidoList);

		return new ResponseEntity<List<DongCode>>(gugunList, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/getDong")
	public ResponseEntity<List<DongCode>> getDong(@RequestBody Map<String, String> gugunCode) {

		List<DongCode> dongList = houseService.getDong(gugunCode);
		//System.out.println(dongList);
		
		return new ResponseEntity<List<DongCode>>(dongList, HttpStatus.OK);
	}
	
	@PostMapping
	@RequestMapping("/getHouseList")
	public ResponseEntity<List<HouseInfo>> getHouseList(@RequestBody Map<String, Object> item){
		
		List<HouseInfo> houseList = houseService.getHouseList(item);
		
		return new ResponseEntity<List<HouseInfo>>(houseList, HttpStatus.OK);
	}
	
	@PostMapping
	@RequestMapping("/addFavorite")
	public ResponseEntity<Map<String, String>> addFavorite(@RequestBody Map<String, String> item){
		
		Map<String, String> resultMap = new HashMap<>();
		
		int i = houseService.checkFavorite(item);
		
		if(i>0) {
			resultMap.put("message", "이미 추가된 매물입니다");
		}else {
			int j = houseService.addFavorite(item);
			if(j>0) {
				resultMap.put("message", "관심매물 등록 성공");
			}else {
				resultMap.put("message", "등록중 오류 발생");
			}
		}
		
		
		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
	}
	
	@PostMapping
	@RequestMapping("/getFavorite")
	public ResponseEntity<List<HouseInfo>> getFavorite(@RequestBody Map<String, String> user){
		
		List<HouseInfo> list = houseService.getFavorite(user);		
		return new ResponseEntity<List<HouseInfo>>(list, HttpStatus.OK);
	}
	
	@PostMapping
	@RequestMapping("/getAptInfo")
	public ResponseEntity<HouseInfo> getAptInfo(@RequestBody Map<String, String> code){
		
		HouseInfo info = houseService.getAptInfo(code);
		
		if(info!=null) {
			return new ResponseEntity<HouseInfo>(info, HttpStatus.OK);
		}else {
			return null;
		}
	}
	
	@PostMapping
	@RequestMapping("/getSchool")
	public ResponseEntity<List<School>> getSchool(@RequestBody HouseInfo houseinfo){
		
		List<School> list = houseService.getSchool(houseinfo);
		//System.out.println(list);
		return new ResponseEntity<List<School>>(list, HttpStatus.OK);
		
	}
	
	@PostMapping
	@RequestMapping("/deleteFavorite")
	public ResponseEntity<Map<String, String>> deleteFavorite(@RequestBody Map<String, String> item){
		Map<String, String> resultMap = new HashMap<>();
		
		int i = houseService.deleteFavorite(item);
		if(i>0) {
			resultMap.put("message", "삭제 성공");
		}else {
			resultMap.put("message","삭제 실패");
		}
		
		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
	}
	
	@PostMapping
	@RequestMapping("/getDealLog")
	public ResponseEntity<List<DealLog>> getDealLog(@RequestBody Map<String, String> aptCode){
		
		List<DealLog> list = houseService.getDealLog(aptCode);
		
		return new ResponseEntity<List<DealLog>>(list, HttpStatus.OK);
		
	}
	
	@PostMapping
	@RequestMapping("/getAvgDong")
	public ResponseEntity<List<DealLog>> getAvgDong(@RequestBody Map<String, String> dongCode){
		
		List<DealLog> list = houseService.getAvgDong(dongCode);
		//System.out.println(list);
		return new ResponseEntity<List<DealLog>>(list, HttpStatus.OK);
	}

}
