package com.ssafy.member.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.member.mapper.MemberMapper;
import com.ssafy.member.dto.LoginSessionModel;
import com.ssafy.member.dto.MemberDto;
import com.ssafy.member.dto.SecVO;
import com.ssafy.member.dto.SecurityModel;
import com.ssafy.util.OpenCrypt;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;

	// 1. 로그인
	@Override
	public MemberDto login(MemberDto memberDto) throws Exception {
		if (memberDto.getId() == null || memberDto.getPassword() == null)
			return null;
		System.out.println("로그인2");
		return memberMapper.login(memberDto);
	}

	// 2. user info
	@Override
	public MemberDto userInfo(String id) throws Exception {
		return memberMapper.userInfo(id);
	}

	public MemberDto nicknameCheck(String nickname) throws Exception {
		return memberMapper.nicknameCheck(nickname);
	}

	public MemberDto pwCheck(MemberDto m) {
		SecVO sec = memberMapper.selectSecurity(m.getId());
		m.setPassword(new String(OpenCrypt.getSHA256(m.getPassword(), sec.getSalt())));
		return memberMapper.pwCheck(m);
	}

	// 3. regist
	public int regist(MemberDto m) {
		return memberMapper.regist(m);
	}

	public int delete(MemberDto m) {
		try {
			memberMapper.deleteFavorite(m);
			memberMapper.deleteSubboard(m);
			memberMapper.deleteBoard(m);
			memberMapper.deleteLoginHistory(m);
			memberMapper.deleteSecurity(m);
			memberMapper.deleteMember(m);
			return 1;
		} catch (Exception e) {
			return 0; // success
		}

	}

	public boolean updateMember(MemberDto member) {
		try {
			SecVO sec = memberMapper.selectSecurity(member.getId());
			member.setPassword(new String(OpenCrypt.getSHA256(member.getPassword(), sec.getSalt())));
			System.out.println(member);
			System.out.println("여기까진 된다고 ㅇㅇ");
			int i = memberMapper.updateMember(member);
			System.out.println("service - 멤버수정");
			return true;
		} catch (Exception e) {
			System.out.println("응 수정안돼~");
			return false;
		}

	}

	public MemberDto findMember(String id) {
		MemberDto m = memberMapper.userInfo(id);
		SecVO sec = memberMapper.selectSecurity(id);
		try {
			m.setNickname(OpenCrypt.aesDecrypt(m.getNickname(), OpenCrypt.hexToByteArray(sec.getSecKey())));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
	}

	@Override
	public void saveRefreshToken(String id, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", refreshToken);
		memberMapper.saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String id) throws Exception {
		return memberMapper.getRefreshToken(id);
	}

	@Override
	public void deleRefreshToken(String id) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("token", null);
		memberMapper.deleteRefreshToken(map);
	}

	@Transactional
	public int addMember(MemberDto member) {
		if (memberMapper.userInfo(member.getId()) != null) {
			return 1;
		} else {
			try {
				byte[] key = OpenCrypt.generateKey("AES", 128);
				System.out.println("key length:" + key.length);
				SecVO sec = new SecVO(member.getId(), UUID.randomUUID().toString(), OpenCrypt.byteArrayToHex(key));
				System.out.println("memberservice 단에서" + member);
				System.out.println("memberService - 일로는왔어");
				member.setPassword(new String(OpenCrypt.getSHA256(member.getPassword(), sec.getSalt())));
				memberMapper.insertMember(member);
				memberMapper.insertSecurity(sec);
				memberMapper.insertLoginHistory(member);
				// System.out.println(member.getId() + " : " + member.getNickname());
				return 3;
			} catch (Exception e) {
				e.printStackTrace();
				return 2;
			}
		}
	}

	public LoginSessionModel checkUserId(MemberDto m2) {
		SecVO sec = memberMapper.selectSecurity(m2.getId());
		String userPw = m2.getPassword();
		userPw = new String(OpenCrypt.getSHA256(m2.getPassword(), sec.getSalt()));
		m2.setPassword(userPw);
		System.out.println("service - m2 : " + m2);
		MemberDto member;
		try {
			member = memberMapper.login(m2);
			System.out.println("service - member : " + member);
			if (member != null) {
				LoginSessionModel m = new LoginSessionModel(member.getId(), member.getPassword(), member.getNickname(),
						false);
				return m;
			} else {
				System.out.println("user check failed");
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("user check error");
			return null;
		}

	}

	public SecurityModel checkSecurity(String userId) {
		// TODO Auto-generated method stub
		return memberMapper.checkSecurity(userId);
	}

	public void updateSecurityModel(SecurityModel sec) {
		memberMapper.updateSecurityModel(sec);
	}

	public void insertSecurityModel(SecurityModel sec) {
		memberMapper.insertSecurityModel(sec);
	}

}
