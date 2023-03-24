package com.ssafy.board.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.Comment;
import com.ssafy.board.mapper.BoardMapper;

@Service
public class BoardService {
	
	@Autowired
	BoardMapper boardMapper;

	public String boardCount() {
		// TODO Auto-generated method stub
		return boardMapper.boardCount();
	}

	public List selectBoard(int n) {
		// TODO Auto-generated method stub
		return boardMapper.selectBoard(n);
	}

	public int writeBoard(Board board) {
		// TODO Auto-generated method stub
		return boardMapper.writeBoard(board);
	}

	public Board getArticle(int num) {
		// TODO Auto-generated method stub
		return boardMapper.getArticle(num);
	}

	public List<Comment> getComment(Map<String, Object> no) {
		// TODO Auto-generated method stub
		return boardMapper.getComment(no);
	}

	public int addComment(Comment comment) {
		// TODO Auto-generated method stub
		return boardMapper.addComment(comment);
	}

	public void addHits(int num) {
		boardMapper.addHits(num);
	}

	public int getMaxCode() {
		// TODO Auto-generated method stub
		return boardMapper.getMaxCode();
	}

	public int removeComment(Map<String, Integer> no) {
		// TODO Auto-generated method stub
		return boardMapper.removeComment(no);
	}

	public int modifyComment(Comment comment) {
		// TODO Auto-generated method stub
		return boardMapper.modifyComment(comment);
	}

	public int modifyBoard(Board board) {
		// TODO Auto-generated method stub
		return boardMapper.modifyBoard(board);
	}

	public int deleteBoard(Map<String, Integer> item) {
		// TODO Auto-generated method stub
		return boardMapper.deleteBoard(item);
	}

	public int deleteAllComment(Map<String, Integer> item) {
		// TODO Auto-generated method stub
		return boardMapper.deleteAllComment(item);
	}

	public List<Board> searchTitle(Map<String, String> text) {
		// TODO Auto-generated method stub
		return boardMapper.searchTitle(text);
	}

	public List<Board> searchTitlePage(Map<String, Object> item) {
		// TODO Auto-generated method stub
		return boardMapper.searchTitlePage(item);
	}

	public String countArticleText(Map<String, String> text) {
		// TODO Auto-generated method stub
		return boardMapper.countArticleText(text);
	}

	public String getNickname(Map<String, Object> payloads) {
		// TODO Auto-generated method stub
		return boardMapper.getNickname(payloads);
	}

	public int countComment(Map<String, Object> no) {
		// TODO Auto-generated method stub
		return boardMapper.countComment(no);
	}

	public int upCount(Map<String, Object> code) {
		return boardMapper.upCount(code);
		
	}

	public int downCount(Map<String, Object> code) {
		// TODO Auto-generated method stub
		return boardMapper.downCount(code);
		
	}

	public int addLike(Map<String, Object> item) {
		// TODO Auto-generated method stub
		return boardMapper.addLike(item);
	}

	public int deleteLike(Map<String, Object> item) {
		// TODO Auto-generated method stub
		return boardMapper.deleteLike(item);
	}

	public int checkLike(Map<String, Object> item) {
		// TODO Auto-generated method stub
		return boardMapper.checkLike(item);
	}

	
}
