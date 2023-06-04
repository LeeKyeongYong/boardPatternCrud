package com.study.board.model;

import java.util.Map;

public class ArticleReplyDTO extends ModelDTO{

    private int articleId;
    private String body;

    public ArticleReplyDTO() {
    }

    public ArticleReplyDTO( int articleId, String body) {
        this.articleId = articleId;
        this.body = body;
    }

    public ArticleReplyDTO(Map<String, Object> row) {
        super(row);
        this.body=(String) row.get("body");
        this.articleId=(int)row.get("articleId");
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }
}
