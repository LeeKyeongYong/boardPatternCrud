package com.study.board.pattern;

import com.study.board.dao.ArticleDAO;
import com.study.board.service.ArticleService;
import com.study.board.service.BuildServcie;
import com.study.board.util.DbConnectionUtil;
import com.study.board.util.SessionUtil;

import java.util.Scanner;

public class DesignFactory {

    private static SessionUtil session;
    private static DbConnectionUtil dbConnection;
    private static BuildServcie buildServcie;
    private static ArticleService articleService;
    private static ArticleDAO articleDAO;
    private static Scanner sc;

    public static DbConnectionUtil getDbConnection(){
        if(dbConnection==null){
            dbConnection=new DbConnectionUtil();
        }
        return dbConnection;
    }

    public static SessionUtil getSession(){
        if(session ==null){
            session=new SessionUtil();
        }
        return session;
    }

    public static Scanner getScanner(){
        if(sc==null){
            sc=new Scanner(System.in);
        }
        return sc;
    }

    public static ArticleService getArticleService(){
        if(articleService == null){
            articleService = new ArticleService();
        }
        return articleService;
    }

    public static ArticleDAO getArticleDAO(){
        if(articleDAO==null){
            articleDAO=new ArticleDAO();
        }
        return articleDAO;
    }
    public static BuildServcie getBuildServcie(){
        if(buildServcie==null){
            buildServcie = new BuildServcie();
        }
        return buildServcie;
    }
}
