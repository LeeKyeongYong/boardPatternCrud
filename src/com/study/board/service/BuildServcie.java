package com.study.board.service;

import com.study.board.model.ArticleDTO;
import com.study.board.model.BoardDTO;
import com.study.board.pattern.DesignFactory;
import com.study.board.util.DfldUtil;

import java.util.List;

public class BuildServcie {
    private ArticleService articleService;

    public BuildServcie(){
        articleService = DesignFactory.getArticleService();
    }
    public void buildSite(){
        DfldUtil.mkDir("site");
        DfldUtil.mkDir("site/article");

        String head = DfldUtil.getFileContents("site_template/inc/head.html");
        String foot = DfldUtil.getFileContents("site_template/inc/foot.html");

        // 각 게시판 별 게시물리스트 페이지 생성
        List<BoardDTO> boards = articleService.getBoards();

        for(BoardDTO dto:boards){

            String fileName = dto.getCode()+"-list-1.html";

            String html="";

            List<ArticleDTO> articles = articleService.getAricleByBoardCode(dto.getCode());

            String template = DfldUtil.getFileContents("site_template/article/list.html");

            for(ArticleDTO article:articles){
                html+="<tr>";
                html+="<td>"+article.getId()+"</td>";
                html+="<td>"+article.getWriteDate()+"</td>";
                html+="<td><a href=\""+article.getId()+".html\">"+article.getTitle()+"</a></td>";
                html+="</tr>";

            }

            html = template.replace("${TR}",html);

            html = head+html+foot;

            DfldUtil.writeFileContents("site/article/"+fileName,html);

        }

        //게시물 별 파일 생성
        List<ArticleDTO> articles = articleService.getArticles();
        for(ArticleDTO article:articles){
            String html="";
            html+="<div> 제목 : "+article.getTitle()+"</div>";
            html+="<div> 내용 : "+article.getBody()+"</div>";
            html+="<div><a href=\""+(article.getId()-1)+".html\">이전글</a></div>";
            html+="<div><a href=\""+(article.getId()+1)+".html\">다음글</a></div>";
            html=head+html+foot;
            DfldUtil.writeFileContents("site/article/"+article.getId()+".html",html);

        }

    }
}
