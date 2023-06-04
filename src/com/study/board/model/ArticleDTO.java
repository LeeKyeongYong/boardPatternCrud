package com.study.board.model;

import java.util.Map;

public class ArticleDTO extends ModelDTO{

    private int boardId;
    private String title;
    private String body;

    public ArticleDTO() {

    }

    public ArticleDTO(int boardId, String title, String body) {
        this.boardId = boardId;
        this.title = title;
        this.body = body;
    }

    public ArticleDTO(Map<String,Object> row) {
        this.boardId = (int)row.get("boardId");
        this.title = (String)row.get("title");
        this.body = (String)row.get("body");
    }

    public int getBoardId() {
        return boardId;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "ArticleDTO{" +
                "boardId=" + boardId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
