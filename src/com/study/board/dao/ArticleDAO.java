package com.study.board.dao;

import com.study.board.model.ArticleDTO;
import com.study.board.model.ArticleReplyDTO;
import com.study.board.model.BoardDTO;
import com.study.board.pattern.DesignFactory;
import com.study.board.util.DbConnectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ArticleDAO {
    private DbConnectionUtil dbConnection;

    public ArticleDAO() {
        this.dbConnection = DesignFactory.getDbConnection();
    }

    public List<ArticleDTO> getArticlesByBoardCode(String code){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("select A.* "));
        sb.append(String.format("FROM `aricle` as A "));
        sb.append(String.format("INNER JOIN `board` AS B "));
        sb.append(String.format("ON A.boardId = B.id "));
        sb.append(String.format("WHERE 1 "));
        sb.append(String.format("AND B.`code` = '%s' ",code));
        sb.append(String.format("ORDER BY A.id DESC"));

        List<ArticleDTO> articles = new ArrayList<>();
        List<Map<String,Object>> rows = dbConnection.selectRows(sb.toString());
        for(Map<String,Object> row: rows){
            articles.add(new ArticleDTO(row));
        }

        return articles;
    }

    public List<BoardDTO> getBoards(){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `board` "));
        sb.append(String.format("WHERE 1 "));
        sb.append(String.format("ORDER BY id DESC"));

        List<BoardDTO> boards = new ArrayList<>();
        List<Map<String,Object>> rows=dbConnection.selectRows(sb.toString());

        for(Map<String,Object>row:rows){
            boards.add(new BoardDTO(row));
        }

        return boards;
    }

    public BoardDTO getBoardByCode(String code){

        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `board` "));
        sb.append(String.format("WHERE 1 "));
        sb.append(String.format("AND `code` = '%s' ",code));


        Map<String,Object> row=dbConnection.selectRow(sb.toString());

        if(row.isEmpty()){
            return null;
        }

        return new BoardDTO(row);
    }

    public int saveBoard(BoardDTO dto){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("INSERT INTO board "));
        sb.append(String.format("SET WriteDate = '%s' ",dto.getWriteDate()));
        sb.append(String.format(", `code` = '%s' ",dto.getCode()));
        sb.append(String.format(", `name` = '%s' ",dto.getName()));

        return dbConnection.insert(sb.toString());
    }

    public int save(ArticleDTO dto){
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("INSERT INTO article "));
        sb.append(String.format("SET WriteDate = '%s' ",dto.getWriteDate()));
        sb.append(String.format(", `title` = '%s' ",dto.getTitle()));
        sb.append(String.format(", `body` = '%s' ",dto.getBody()));
        sb.append(String.format(", 'boardId' = '%d' ",dto.getBoardId()));

        return dbConnection.insert(sb.toString());
    }

    public BoardDTO getBoard(int id){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `board` "));
        sb.append(String.format("WHERE 1 "));
        sb.append(String.format("AND `id` = '%d' ",id));

        Map<String,Object> row = dbConnection.selectRow(sb.toString());

        if(row.isEmpty()){
            return null;
        }

        return new BoardDTO(row);
    }

    public List<ArticleDTO> getArticles(){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `article` "));
        sb.append(String.format("WHERE 1"));
        sb.append(String.format("ORDER BY id DESC "));

        List<ArticleDTO> articles = new ArrayList<>();
        List<Map<String,Object>> rows = dbConnection.selectRows(sb.toString());

        for(Map<String,Object> row: rows){
            articles.add(new ArticleDTO(row));
        }

        return articles;
    }

    public ArticleDTO getArticle(int id){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `article` "));
        sb.append(String.format("WHERE id ='%d' ",id));

        String sql=sb.toString();
        Map<String,Object> row=dbConnection.selectRow(sql);

        if(row.isEmpty()){
            return null;
        }
        return new ArticleDTO(row);
    }

    public ArticleDTO getForPrintAricle(int id){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT A.* "));
        sb.append(String.format("FROM `article` as A "));
        sb.append(String.format("WHERE A.id = '%d' ",id));

        String sql=sb.toString();
        Map<String,Object> row = dbConnection.selectRow(sql);

        if(row.isEmpty()){
            return null;
        }

        return new ArticleDTO(row);
    }

    //게시물 수정
    public void modify(int modiNum,String newTitle,String newBody){
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("UPDATE `article` SET "));
        sb.append(String.format("title = '%s'",newTitle));
        sb.append(String.format(", `body` = '%s' ",newBody));
        sb.append(String.format("where `id` = '%d'",modiNum));

        dbConnection.update(sb.toString());
    }

    //게시물 삭제
    public void delete(int delNum){
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("DELETE FROM `article` "));
        sb.append(String.format(" WHERE `id` = '%d'",delNum));
        dbConnection.insert(sb.toString());
    }

    //댓글
    public List<ArticleReplyDTO> getArticleRepliesByArticleId(int articleId){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT * "));
        sb.append(String.format("FROM `articleReply` "));
        sb.append(String.format("WHERE articleID = '%d' ",articleId));
        sb.append(String.format("ORDER BY id DESC"));

        List<ArticleReplyDTO> articleReplies = new ArrayList<>();
        List<Map<String,Object>> rows=dbConnection.selectRows(sb.toString());

        for(Map<String,Object>row:rows){
            articleReplies.add(new ArticleReplyDTO(row));
        }
        return articleReplies;
    }

    public List<ArticleReplyDTO> getforPrintArticleReplicesByArticleId(int articleId){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT AR.* "));
        sb.append(String.format("FROM `articleReply` AS AR "));
        sb.append(String.format("WHERE AR.articleID = '%d' "));
        sb.append(String.format("ORDER BY AR.id DESC "));

        List<ArticleReplyDTO> articleReplies = new ArrayList<>();
        List<Map<String,Object>> rows = dbConnection.selectRows(sb.toString());

        for(Map<String,Object> row:rows){
            articleReplies.add(new ArticleReplyDTO(row));
        }
        return articleReplies;
    }

    public List<ArticleDTO> getForPrintArticlesByBoardCode(String code){
        StringBuilder sb=new StringBuilder();
        sb.append(String.format("SELECT A.* "));
        sb.append(String.format("FROM `article` AS A "));
        sb.append(String.format("INNER JOIN `board` AS B "));
        sb.append(String.format("ON A.boardId = B.id "));
        sb.append(String.format("WHERE B.code = '%s' ",code));
        sb.append(String.format("ORDER BY A.id DESC "));

        List<ArticleDTO> articles = new ArrayList<>();
        List<Map<String,Object>> rows =dbConnection.selectRows(sb.toString());

        for(Map<String,Object> row: rows){
            articles.add(new ArticleDTO(row));
        }
        return articles;
    }

    //번호에 해당하는 댓글 가져오기
    public ArticleReplyDTO getArticleReply(int id){
        StringBuilder sb=new StringBuilder();

        sb.append(String.format("SELECT *"));
        sb.append(String.format("FROM `articleReply` "));
        sb.append(String.format("WHERE id = '%d' ",id));

        String sql=sb.toString();
        Map<String,Object> row = dbConnection.selectRow(sql);
        if(row.isEmpty()){
            return null;
        }
        return new ArticleReplyDTO(row);
    }

    //댓글 작성
    public int reply(ArticleReplyDTO articleReply){

        StringBuilder sb=new StringBuilder();
        sb.append(String.format("INSERT INTO articleReply "));
        sb.append(String.format("SET WriteDate = '%s' ",articleReply.getWriteDate()));
        sb.append(String.format(", `body` = '%s' ",articleReply.getBody()));
        sb.append(String.format(", `articleId` = '%d' ",articleReply.getArticleId()));

        return dbConnection.insert(sb.toString());

    }

    //댁글 삭제
    public void ReplyDelete(int delNum){

        StringBuilder sb=new StringBuilder();
        sb.append(String.format("DELETE FROM `articleReply` "));
        sb.append(String.format("WHERE `id` = '%d' ",delNum));

        dbConnection.insert(sb.toString());
    }

}
