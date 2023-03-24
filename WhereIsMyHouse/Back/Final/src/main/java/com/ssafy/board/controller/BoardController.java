package com.ssafy.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhncorp.lucy.security.xss.LucyXssFilter;
import com.nhncorp.lucy.security.xss.XssSaxFilter;
import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.Comment;
import com.ssafy.board.service.BoardService;

@RequestMapping("/board")
@Controller
public class BoardController {

	@Autowired
	BoardService boardService;

	@PostMapping("/count")
	public ResponseEntity<Map<String, String>> boardCount() {
		String count = boardService.boardCount();
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("count", count);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);

	}

	@GetMapping("/selectBoard")
	public ResponseEntity<List<Board>> selectBoard(@RequestParam int num) {
		int n = (num - 1) * 10;
		List boardList = boardService.selectBoard(n);
		if (boardList != null) {
			return new ResponseEntity<List<Board>>(boardList, HttpStatus.OK);
		} else {
			return null;
		}

	}

	@PostMapping
	@RequestMapping("/writeBoard")
	public ResponseEntity<Map<String, Object>> writeBoard(@RequestBody Board board, HttpServletRequest request) {
		Map<String, Object> resultMap = new HashMap<>();

		// 글 쓴 사용자 == 현재 사용자인지 확인
		String token = request.getHeader("access-token");
		String authToken = token.substring(7);
		String[] strings = authToken.split("\\.");
		Map<String, Object> payloads = null;

		try {
			payloads = new ObjectMapper().readValue(new String(Base64.decodeBase64(strings[1])), Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String userNickname = boardService.getNickname(payloads);

		if (!userNickname.equals(board.getNickname())) {
			return null;
		}

		int i = boardService.writeBoard(board);

		if (i > 0) {
			resultMap.put("message", "글 등록 성공");
		} else {
			resultMap.put("message", "글 등록 실패");
		}

		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/getArticle")
	public ResponseEntity<Board> getArticle(@RequestBody Map<String, String> no) {
		// System.out.println(no);
		int num = Integer.parseInt(no.get("item"));
		
		LucyXssFilter filter = XssSaxFilter.getInstance("lucy-xss-sax.xml", true);
		Board board = boardService.getArticle(num);
		
		ObjectMapper mapper = new ObjectMapper();
		Board cleanBoard = null;
		String json;
		try {
			json = mapper.writeValueAsString(board);
			String clean = filter.doFilter(json);
			//		System.out.println(clean);
			cleanBoard = mapper.readValue(clean, Board.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		return new ResponseEntity<>(cleanBoard, HttpStatus.OK);

	}

	@PostMapping
	@RequestMapping("/addHits")
	@ResponseBody
	public void addHits(@RequestBody Map<String, String> no) {

		int num = Integer.parseInt(no.get("item"));

		boardService.addHits(num);

	}

	@PostMapping
	@RequestMapping("/searchTitle")
	public ResponseEntity<List<Board>> searchTitle(@RequestBody Map<String, String> text) {

		List<Board> board = boardService.searchTitle(text);

		return new ResponseEntity<List<Board>>(board, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/searchTitlePage")
	public ResponseEntity<List<Board>> searchTitlePage(@RequestBody Map<String, Object> item) {
		int num = ((int) item.get("page") - 1) * 10;

		item.put("num", num);

		List<Board> board = boardService.searchTitlePage(item);

		return new ResponseEntity<List<Board>>(board, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/countArticleText")
	public ResponseEntity<Map<String, String>> countArticleText(@RequestBody Map<String, String> text) {
		String count = boardService.countArticleText(text);
		Map<String, String> resultMap = new HashMap<>();
		resultMap.put("count", count);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);

	}

	@PostMapping
	@RequestMapping("/getMaxCode")
	public ResponseEntity<Map<String, Integer>> getMaxCode() {

		Map<String, Integer> resultMap = new HashMap<>();
		int i = boardService.getMaxCode();

		if (i >= 0) {
			resultMap.put("num", i);
		}

		return new ResponseEntity<Map<String, Integer>>(resultMap, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/modifyBoard")
	public ResponseEntity<Map<String, String>> modifyBoard(@RequestBody Board board, HttpServletRequest request) {

		Map<String, String> resultMap = new HashMap<>();

		String token = request.getHeader("access-token");
		String authToken = token.substring(7);
		String[] strings = authToken.split("\\.");
		Map<String, Object> payloads = null;

		try {
			payloads = new ObjectMapper().readValue(new String(Base64.decodeBase64(strings[1])), Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String userNickname = boardService.getNickname(payloads);

		if (!userNickname.equals(board.getNickname())) {
			return null;
		}

		// System.out.println(board);
		int i = boardService.modifyBoard(board);

		if (i > 0) {
			resultMap.put("message", "수정 완료");
		} else {
			resultMap.put("message", "수정 중 오류 발생");
		}

		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/deleteBoard")
	public ResponseEntity<Map<String, String>> deleteBoard(@RequestBody Map<String, Integer> item) {

		Map<String, String> resultMap = new HashMap<>();

		int j = boardService.deleteAllComment(item);
		int i = boardService.deleteBoard(item);

		if (i > 0 && j > 0) {
			resultMap.put("message", "삭제 완료");
		} else {
			resultMap.put("message", "삭제 중 오류 발생");
		}

		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
	}
	////////////////////// 댓글////////////////////////////////////////////////

	@PostMapping
	@RequestMapping("/getComment")
	public ResponseEntity<List<Comment>> getComment(@RequestBody Map<String, Object> no) {

		List<Comment> list = boardService.getComment(no);
		// System.out.println(list);

		return new ResponseEntity<List<Comment>>(list, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/addComment")
	public ResponseEntity<Map<String, String>> getComment(@RequestBody Comment comment, HttpServletRequest request) {
		Map<String, String> resultMap = new HashMap<>();

		String token = request.getHeader("access-token");
		String authToken = token.substring(7);
		String[] strings = authToken.split("\\.");
		Map<String, Object> payloads = null;

		try {
			payloads = new ObjectMapper().readValue(new String(Base64.decodeBase64(strings[1])), Map.class);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		String userNickname = boardService.getNickname(payloads);

		if (!userNickname.equals(comment.getNickname())) {
			return null;
		}

		int i = boardService.addComment(comment);

		if (i > 0) {
			resultMap.put("message", "댓글입력성공");
		} else {
			resultMap.put("message", "댓글입력실패");
		}

		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/removeComment")
	public ResponseEntity<Map<String, String>> removeComment(@RequestBody Map<String, Integer> no) {

		Map<String, String> resultMap = new HashMap<>();
		// System.out.println(num);
		int i = boardService.removeComment(no);

		System.out.println(i);

		if (i > 0) {
			resultMap.put("message", "삭제 완료");
		} else {
			resultMap.put("message", "삭제 중 오류 발생");
		}

		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/modifyComment")
	public ResponseEntity<Map<String, String>> modifyComment(@RequestBody Comment comment) {
		Map<String, String> resultMap = new HashMap<>();
		int i = boardService.modifyComment(comment);

		if (i > 0) {
			resultMap.put("message", "수정 완료");
		} else {
			resultMap.put("message", "수정 중 오류 발생");
		}

		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
	}

	@PostMapping
	@RequestMapping("/countComment")
	public ResponseEntity<Map<String, Object>> countComment(@RequestBody Map<String, Object> no) {
		Map<String, Object> resultMap = new HashMap<>();

		int num = boardService.countComment(no);
		resultMap.put("size", num);

		return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);

	}

	@PostMapping
	@RequestMapping("/upCount")
	public ResponseEntity<Map<String, String>> upCount(@RequestBody Map<String, Object> code) {

		int i = boardService.upCount(code);

		return null;
	}

	@PostMapping
	@RequestMapping("/downCount")
	public ResponseEntity<Map<String, String>> downCount(@RequestBody Map<String, Object> code) {

		int i = boardService.downCount(code);

		return null;
	}

	@PostMapping
	@RequestMapping("/addLike")
	public ResponseEntity<Map<String, String>> addLike(@RequestBody Map<String, Object> item) {

		int i = boardService.addLike(item);

		return null;
	}
	
	@PostMapping
	@RequestMapping("/deleteLike")
	public ResponseEntity<Map<String, String>> deleteLike(@RequestBody Map<String, Object> item) {

		int i = boardService.deleteLike(item);

		return null;
	}
	
	@RequestMapping("/checkLike")
	public ResponseEntity<Map<String, String>> checkLike(@RequestBody Map<String, Object> item) {
		Map<String, String> resultMap = new HashMap<>();
		int i = boardService.checkLike(item);
//		System.out.println(item);
//		System.out.println(i);
		if(i>0) {
			resultMap.put("toggle", "true");
		}else {
			resultMap.put("toggle", "false");
		}

		return new ResponseEntity<Map<String, String>>(resultMap, HttpStatus.OK);
	}
	
}
