package com.ssafy.board.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.board.dto.Board;
import com.ssafy.board.dto.Comment;

@Mapper
public interface BoardMapper {

	String boardCount();

	List selectBoard(int n);

	int writeBoard(Board board);

	Board getArticle(int num);

	List<Comment> getComment(Map<String, Object> no);

	int addComment(Comment comment);

	void addHits(int num);

	int getMaxCode();

	int removeComment(Map<String, Integer> no);

	int modifyComment(Comment comment);

	int modifyBoard(Board board);

	int deleteBoard(Map<String, Integer> item);

	int deleteAllComment(Map<String, Integer> item);

	List<Board> searchTitle(Map<String, String> text);

	List<Board> searchTitlePage(Map<String, Object> item);

	String countArticleText(Map<String, String> text);

	String getNickname(Map<String, Object> payloads);

	int countComment(Map<String, Object> no);

	int upCount(Map<String, Object> code);

	int downCount(Map<String, Object> code);

	int addLike(Map<String, Object> item);

	int deleteLike(Map<String, Object> item);

	int checkLike(Map<String, Object> item);

}
