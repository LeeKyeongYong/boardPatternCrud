package com.study.board.service;

import com.study.board.dao.ArticleDAO;
import com.study.board.model.ArticleDTO;
import com.study.board.model.ArticleReplyDTO;
import com.study.board.model.BoardDTO;
import com.study.board.pattern.DesignFactory;

import java.util.List;

public class ArticleService {
    private ArticleDAO articleDAO;

    public ArticleService() {
        this.articleDAO = DesignFactory.getArticleDAO();
    }

    public List<ArticleDTO> getAricleByBoardCode(String code){
        return articleDAO.getArticlesByBoardCode(code);
    }

    public List<BoardDTO> getBoards(){
        return articleDAO.getBoards();
    }

    public int makeBoard(String name,String code){
        BoardDTO oldDto = articleDAO.getBoardByCode(code);

        if(oldDto!=null){
            return -1;
        }
        BoardDTO dto=new BoardDTO(name,code);
        return articleDAO.saveBoard(dto);
    }

    public BoardDTO getBoard(int id){
        return articleDAO.getBoard(id);
    }

    public int write(int boardId,String title,String body){
        ArticleDTO article = new ArticleDTO(boardId,title,body);
        return articleDAO.save(article);
    }

    public List<ArticleDTO> getArticles(){
        return articleDAO.getArticles();
    }

    public void makeBoardIfNotExists(String name,String code){
        BoardDTO dto= articleDAO.getBoardByCode(code);
        if(dto==null){
            makeBoard(name,code);
        }
    }

    public BoardDTO getBoardByCode(String code){
        return articleDAO.getBoardByCode(code);
    }

    public ArticleDTO getArticle(int id){
        return articleDAO.getArticle(id);
    }

    public ArticleDTO getForPrintArticle(int id){
        return articleDAO.getForPrintAricle(id);
    }

    //게시물 수정
    public void modify(int modiNum,String newTitle,String newBody){
        articleDAO.modify(modiNum,newTitle,newBody);
    }

    //게시물 삭제
    public void delete(int delNum){
        articleDAO.delete(delNum);
    }

    //댓글
    public List<ArticleReplyDTO> getArticleReplicesByArticleId(int articleId){
        return articleDAO.getArticleRepliesByArticleId(articleId);
    }

    public List<ArticleReplyDTO> getForPrintArticleRepliesByArticleId(int articleId){
        return articleDAO.getforPrintArticleReplicesByArticleId(articleId);
    }

    public List<ArticleDTO> getForPrintArticlesByBoardCode(String code){
        return articleDAO.getForPrintArticlesByBoardCode(code);
    }

    public ArticleReplyDTO getArticleReply(int id){
        return articleDAO.getArticleReply(id);
    }

    //댓글작성
    public int reply(int articleId,String replyText){
        ArticleReplyDTO articleReply=new ArticleReplyDTO(articleId,replyText);
        return articleDAO.reply(articleReply);
    }

    //댓글 삭제
    public void replyDelete(int delNum){
        articleDAO.ReplyDelete(delNum);
    }

}
