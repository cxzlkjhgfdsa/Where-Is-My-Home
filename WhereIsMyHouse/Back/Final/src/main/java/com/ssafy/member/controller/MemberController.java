package com.ssafy.member.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.board.dto.Board;
import com.ssafy.member.dto.LoginSessionModel;
import com.ssafy.member.dto.MemberDto;
import com.ssafy.member.dto.SecurityModel;
import com.ssafy.member.service.JwtServiceImpl;
import com.ssafy.member.service.MemberService;
import com.ssafy.member.service.MemberServiceImpl;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/user")
@Api("사용자 컨트롤러  API V1")
public class MemberController {

	public static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private JwtServiceImpl jwtService;

	@Autowired
	private MemberServiceImpl memberService;

	@ApiOperation(value = "로그인", notes = "Access-token과 로그인 결과 메세지를 반환한다.", response = Map.class)
	@PostMapping("/login")
	public ResponseEntity<Map<String, Object>> login(
			@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) MemberDto memberDto,
			HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		// System.out.println("여긴오냐?");
		SecurityModel sec = null;

		try {
			sec = memberService.checkSecurity(memberDto.getId());
			if (sec != null) {
				System.out.println("sec이 널이 아니래");
				if (sec.getRetry() >= 5) { // 5회 이상이면
					long retryTime = new Date().getTime() - sec.getLastFailedLogin();
					if (retryTime < 30000) {
						System.out.println("야 3회 이상이래");
						return null;
					} else {
						sec.setRetry(0);
						sec.setLastFailedLogin(0);
						System.out.println("그럼 여긴가..?");
						memberService.updateSecurityModel(sec);
						System.out.println("updateSecurity");
					}
				}
			} else { // sec이 널이면 - id가ㅣ 없다
				System.out.println("야 sec이 null이래");
//				sec = new SecurityModel();
//				sec.setId(memberDto.getId());
//				memberService.insertSecurityModel(sec);
				status = HttpStatus.ACCEPTED;
				resultMap.put("message", "noid");
				return new ResponseEntity<Map<String, Object>>(resultMap, status);
			}

			LoginSessionModel loginCheckResult = memberService.checkUserId(memberDto);

			if (loginCheckResult == null) { // 비밀번호가 틀려서 null 이 됐다면
				sec.setId(memberDto.getId());
				sec.setRetry(sec.getRetry() + 1);
				sec.setLastFailedLogin(new Date().getTime());
				memberService.updateSecurityModel(sec);

			}else { // 아니라면
				sec.setRetry(0); // 재시도를 0으로 변경
				sec.setLastFailedLogin(0);
				sec.setLastSuccessedLogin(new Date().getTime());

				sec.setClientIp(request.getRemoteAddr());
				memberService.updateSecurityModel(sec);
//					session.setAttribute("sec",sec);

				memberDto.setId(memberDto.getId());
				memberDto.setPassword(memberDto.getPassword());
				memberDto.setNickname(loginCheckResult.getName());
			}
			MemberDto loginUser = memberService.login(memberDto);
			System.out.println("loginUser : " + loginUser);
			if (loginCheckResult != null) {
				System.out.println("로그인으로 왔어 일단");
				String accessToken = jwtService.createAccessToken("id", loginUser.getId());// key, data
				String refreshToken = jwtService.createRefreshToken("id", loginUser.getId());// key, data
				memberService.saveRefreshToken(memberDto.getId(), refreshToken);

				logger.debug("로그인 accessToken 정보 : {}", accessToken);
				logger.debug("로그인 refreshToken 정보 : {}", refreshToken);
				resultMap.put("access-token", accessToken);
				resultMap.put("refresh-token", refreshToken);
				resultMap.put("message", SUCCESS);

				status = HttpStatus.ACCEPTED;
				System.out.println("로그인완료");
			} else {
				System.out.println("로그인실패 ㅠㅠ");
				resultMap.put("message", FAIL);
				status = HttpStatus.ACCEPTED;
				resultMap.put("retry", sec.getRetry());
				System.out.println(sec.getRetry());
			}
		} catch (Exception e) {
			logger.error("로그인 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
	@GetMapping("/info/{id}")
	public ResponseEntity<Map<String, Object>> getInfo(
			@PathVariable("id") @ApiParam(value = "인증할 회원의 아이디.", required = true) String id,
			HttpServletRequest request) {
//		logger.debug("userid : {} ", userid);
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.UNAUTHORIZED;
		if (jwtService.checkToken(request.getHeader("access-token"))) {
			logger.info("사용 가능한 토큰!!!");
			try {
//				로그인 사용자 정보.
				MemberDto memberDto = memberService.userInfo(id);
				resultMap.put("userInfo", memberDto);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			} catch (Exception e) {
				logger.error("정보조회 실패 : {}", e);
				resultMap.put("message", e.getMessage());
				status = HttpStatus.INTERNAL_SERVER_ERROR;
			}
		} else {
			logger.error("사용 불가능 토큰!!!");
			resultMap.put("message", FAIL);
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
	@GetMapping("/logout/{id}")
	public ResponseEntity<?> removeToken(@PathVariable("id") String id) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		try {
			memberService.deleRefreshToken(id);
			resultMap.put("message", SUCCESS);
			status = HttpStatus.ACCEPTED;
		} catch (Exception e) {
			logger.error("로그아웃 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);

	}

	@ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
	@PostMapping("/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody MemberDto memberDto, HttpServletRequest request)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = HttpStatus.ACCEPTED;
		String token = request.getHeader("refresh-token");
		logger.debug("token : {}, memberDto : {}", token, memberDto);
		if (jwtService.checkToken(token)) {
			if (token.equals(memberService.getRefreshToken(memberDto.getId()))) {
				String accessToken = jwtService.createAccessToken("id", memberDto.getId());
				logger.debug("token : {}", accessToken);
				logger.debug("정상적으로 액세스토큰 재발급!!!");
				resultMap.put("access-token", accessToken);
				resultMap.put("message", SUCCESS);
				status = HttpStatus.ACCEPTED;
			}
		} else {
			logger.debug("리프레쉬토큰도 사용불!!!!!!!");
			status = HttpStatus.UNAUTHORIZED;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	@PostMapping("/regist")
	// @ResponseBody
	public ResponseEntity<Map<String, Object>> regist(@RequestBody MemberDto m, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();

		SecurityModel sec = new SecurityModel();
		sec.setId(m.getId());
		sec.setClientIp(request.getRemoteAddr());
		// System.out.println(m.toString());
		// System.out.println(sec);

		int check = memberService.addMember(m);
		// System.out.println("번호: " + check);
		if (check == 1) {
			resultMap.put("message", "아이디가 중복되어 회원가입에 실패했습니다");
		} else if (check == 2) {
			resultMap.put("message", "에러 발생 회원가입에 실패했습니다");
		} else if (check == 3) {
			resultMap.put("message", "회원가입 성공!");
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);

	}

	@PostMapping("/userinfo")
	public MemberDto userInfo(@RequestBody MemberDto m) {
		System.out.println(m);
		// String id = m.getId();
		MemberDto m2 = null;
		try {
			m2 = memberService.userInfo(m.getId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(m2);
		return m2;
	}

	@PostMapping("/nicknamecheck")
	public MemberDto nicknameCheck(@RequestBody MemberDto m) {
		System.out.println(m);
		// String id = m.getId();
		MemberDto m2 = null;
		try {
			m2 = memberService.nicknameCheck(m.getNickname());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(m2);
		return m2;
	}

	@PostMapping("/modify")
	// @ResponseBody
	public ResponseEntity<Map<String, Object>> modify(@RequestBody MemberDto m) {
		Map<String, Object> resultMap = new HashMap<>();
		boolean updated = memberService.updateMember(m);
		// System.out.println("번호: " + check);
		if (updated) {
			resultMap.put("message", "정상적으로 수정되었습니다.");
		} else {
			resultMap.put("message", "수정에 실패하였습니다.");
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PostMapping("/pwCheck")
	public ResponseEntity<Map<String, Object>> pwCheck(@RequestBody MemberDto m) {
		Map<String, Object> resultMap = new HashMap<>();
		MemberDto member = memberService.pwCheck(m);
		// System.out.println("번호: " + check);
		if (member != null) {
			resultMap.put("message", "verified");
		} else {
			resultMap.put("message", "unverified");
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);

	}
	
	@PostMapping("/delete")
	public ResponseEntity<Map<String, Object>> deleteAll(@RequestBody MemberDto m) {
		Map<String, Object> resultMap = new HashMap<>();
		
		int member = memberService.delete(m);
		
		if (member == 1) {
			resultMap.put("message", "success");
		}else if (member == 2){
			resultMap.put("message", "unknown delete failed");
		}
		return new ResponseEntity<>(resultMap, HttpStatus.OK);

	}


}
