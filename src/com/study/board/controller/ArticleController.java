package com.study.board.controller;

import com.study.board.model.ArticleDTO;
import com.study.board.model.ArticleReplyDTO;
import com.study.board.model.BoardDTO;
import com.study.board.pattern.DesignFactory;
import com.study.board.service.ArticleService;
import com.study.board.util.MasterUtil;
import com.study.board.util.RequestUtil;

import java.util.List;

public class ArticleController extends MasterUtil {

    private ArticleService articleService;

    public ArticleController() {
        this.articleService = DesignFactory.getArticleService();
    }

    @Override
    public void doAction(RequestUtil req) {
        if(req.getActionName().equals("list")){
            actionList(req);
        } else if(req.getActionName().equals("write")){
            actionWrite(req);
        } else if(req.getActionName().equals("modify")){
            actionModify(req);
        } else if(req.getActionName().equals("delete")){
            actionDelete(req);
        } else if(req.getActionName().equals("changeBoard")){
            actionChangeBoard(req);
        } else if(req.getActionName().equals("currentBoard")){
            actionCurrentBoard(req);
        } else if(req.getActionName().equals("detail")){
            actionDetail(req);
        }
    }

    //게시물 상세
    private void actionDetail(RequestUtil req){
        int id;
        try{
            id=Integer.parseInt(req.getArg1());

        }catch(NumberFormatException e){
            System.out.println("게시물 번호를 숫자로 입력하세요.");
            return;
        }

        ArticleDTO article = articleService.getForPrintArticle(id);
        if(article==null){
            System.out.println("해당 게시글은 존재하지 않습니다.");
            return;
        }

        List<ArticleReplyDTO> articleReplies = articleService.getForPrintArticleRepliesByArticleId(article.getId());
        int repliesCount = articleReplies.size();
        System.out.printf("==%d 게시물 상세 시작 ==\n",article.getId());
        System.out.printf("번호: %d\n",article.getId());
        System.out.printf("작성날짜: %s\n",article.getWriteDate());
        System.out.printf("제목: %s\n",article.getTitle());
        System.out.printf("내용: %s\n",article.getBody());
        System.out.printf("댓글개수: %d\n",repliesCount);
        System.out.printf("==%d번 게시물 상세 끝==\n",article.getId());

        System.out.println("댓글을 작성 또는 삭제 하시겠습니까?? < Yes or No>");
        System.out.print("> ");
        String yn=DesignFactory.getScanner().nextLine().trim();
        if(yn.equals("yes")){
            for(ArticleReplyDTO articleReply : articleReplies){
                System.out.printf("%d번 댓글: %s by %s\n",articleReply.getId(),articleReply.getBody(),"anonymous");
            }
            System.out.println("댓글 작성 - 1 / 댓글삭제 - 2");
            System.out.println("> ");
            int wd=DesignFactory.getScanner().nextInt();
            DesignFactory.getScanner().nextLine();

            if(wd==1){
                replyWrite(id);
            } else if(wd==2){
                replyDelete(id);
            } else{
                System.out.println("올바른 숫자를 입력해주세요.");
            }
        } else if(yn.equals("no")){
            System.out.println("명령어로 돌아갑니다.");
            return;
        } else {
            System.out.println("올바른 명령어를 입력해주세요.");
        }
    }

    //댓글 작성
    private void replyWrite(int id){
        System.out.println("댓글을 입력해주세요");
        System.out.print("> ");
        String replyText=DesignFactory.getScanner().nextLine().trim();
        articleService.reply(id,replyText);
        System.out.println("댓글 작성이 완료되었습니다.");
    }

    //댓글 삭제
    private void replyDelete(int id){
        System.out.println("몇번 댓글을 삭제하겠습니까? ");
        System.out.println("> ");
        int delNum=DesignFactory.getScanner().nextInt();

        DesignFactory.getScanner().nextLine();
        ArticleReplyDTO articleReply=articleService.getArticleReply(delNum);
        if(articleReply==null){
            System.out.println("해당 번호의 댓글이 존재하지 않습니다.");
            return;
        }
        articleService.replyDelete(id);
        System.out.println("댓글 삭제가 완료 되었습니다.");
    }

    //현재 접속중인 보드
    public void actionCurrentBoard(RequestUtil req){
        BoardDTO dto=DesignFactory.getSession().getCurrentBoard();
        System.out.printf("현재 게시판: %s\n",dto.getName());
    }

    //보드변경
    private void actionChangeBoard(RequestUtil req){
        String boarCode=req.getArg1();
        BoardDTO dto=articleService.getBoardByCode(boarCode);
        if(dto==null){
            System.out.println("해당 게시판이 존재하지 않습니다.");
        } else {
            System.out.printf("%s 게시판으로 변경 되었습니다.\n",dto.getName());
            DesignFactory.getSession().setCurrentBoard(dto);
        }
    }

    //게시물 리스트
    private void actionList(RequestUtil req){
        BoardDTO dto=DesignFactory.getSession().getCurrentBoard();
        List<ArticleDTO>articles = articleService.getForPrintArticlesByBoardCode(dto.getCode());
        System.out.printf("== %s 게시물 리스트 시작 ==\n",dto.getName());
        System.out.println("번호|                    날짜|제목");
        for(ArticleDTO artciledTo:articles){
            System.out.printf("%4d | %s | %s\n",artciledTo.getId(),artciledTo.getWriteDate(),artciledTo.getTitle());
        }
        System.out.printf("== %s 게시물 리스트 끝== \n",dto.getName());
    }

    //게시물 작성
    private void actionWrite(RequestUtil req){
        System.out.print("제목: ");
        String title=DesignFactory.getScanner().nextLine();
        System.out.print("\n 내용: ");
        String body = DesignFactory.getScanner().nextLine();

        //현재 게시판 id 가져오기
        int boardId=DesignFactory.getSession().getCurrentBoard().getId();

        int newId=articleService.write(boardId,title,body);
        System.out.printf("%d번 글이 생성되었스빈다. \n",newId);
    }

    //게시물 수정
    private void actionModify(RequestUtil req){
        System.out.println("== 게시물 수정 ==");
        System.out.println("수정하실 게시물 번호를 입력해주세요. ");
        System.out.print("> ");

        int modiNum=DesignFactory.getScanner().nextInt();
        DesignFactory.getScanner().nextLine();

        ArticleDTO dto=articleService.getArticle(modiNum);
        if(dto==null){
            System.out.println(" 해당 게시물은 존재하지 않습니다.");
            return;

        }

        System.out.println("수정하실 제목과 내용을 입력해주세요.");
        System.out.print(" 제목: ");
        String newTitle=DesignFactory.getScanner().nextLine().trim();
        System.out.print("\n 내용: ");
        String newBody = DesignFactory.getScanner().nextLine().trim();

        articleService.modify(modiNum,newTitle,newBody);
    }

    //게시물 삭제
    private void actionDelete(RequestUtil req){
        System.out.println("== 게시물 삭제 ==");
        System.out.println("삭제하실 게시물 번호를 입력해주세요. ");
        System.out.print("> ");

        int delNum=DesignFactory.getScanner().nextInt();
        DesignFactory.getScanner().nextLine();

        ArticleDTO dto=articleService.getArticle(delNum);

        if(dto==null){
            System.out.print("해당 게시물은 존재하지 않습니다.");
            return;
        }

        articleService.delete(delNum);
    }
}
