package com.ssafy.member.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.member.dto.MemberDto;
import com.ssafy.member.dto.SecVO;
import com.ssafy.member.dto.SecurityModel;

@Mapper
public interface MemberMapper {

	// User method
	public MemberDto login(MemberDto memberDto) throws SQLException;
	public int regist(MemberDto m);
	public MemberDto userInfo(String userid);
	public int delete(String id);
	public int updateMember(MemberDto member);
	public MemberDto pwCheck(MemberDto member);
	
	// 토큰
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
	
	@Transactional 
	public void insertMember(MemberDto member);
	
	//delete
	public void deleteMember(MemberDto member);
	public void deleteSecurity(MemberDto member);
	public void deleteBoard(MemberDto member);
	public void deleteSubboard(MemberDto member);
	public void deleteLoginHistory(MemberDto member);
	public void deleteFavorite(MemberDto member);
	
	// Secure
	@Transactional 
	public SecurityModel checkSecurity(String userId);
	public void updateSecurityModel(SecurityModel sec);
	public void insertSecurityModel(SecurityModel sec);
	public SecVO selectSecurity(String id);
	public void insertSecurity(SecVO salt);
	public MemberDto nicknameCheck(String nickname);
	public void insertLoginHistory(MemberDto member);
	
	
}
