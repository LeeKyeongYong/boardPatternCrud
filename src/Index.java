import com.study.board.controller.ArticleController;
import com.study.board.controller.BuildController;
import com.study.board.pattern.DesignFactory;
import com.study.board.util.DbConnectionUtil;
import com.study.board.util.MasterUtil;
import com.study.board.util.RequestUtil;

import java.util.HashMap;
import java.util.Map;

public class Index { //게시판 실행하는곳
    private Map<String, MasterUtil> controllers;

    //컨트롤러 만들고 한곳에 정리하기위해 기능정의
    //나중에 컨트롤러 이름으로 쉽게 찾아 사용하기위해 Map사용
    public void initControllers(){
        controllers=new HashMap<>();
        controllers.put("build", new BuildController());
        controllers.put("article", new ArticleController());
    }

    public Index() {
        //컨트롤러 등록
        initControllers();

        //DB정보셋팅
        DbConnectionUtil.DB_NAME="javamvc";
        DbConnectionUtil.DB_USER="test001";
        DbConnectionUtil.DB_PW="root123";
        DbConnectionUtil.DB_PORT=3308;
        DbConnectionUtil.DB_IP="59.130.32.104";//비밀기지 IP
        DesignFactory.getDbConnection().connect();

        //공지사항 게시판 생성
        DesignFactory.getArticleService().makeBoardIfNotExists("공지사항","b01");
        //자유게시판 생성
        DesignFactory.getArticleService().makeBoardIfNotExists("공지사항","b02");

        //현재 게시판을 1번게시판으로 선택
        DesignFactory.getSession().setCurrentBoard(DesignFactory.getArticleService().getBoard(1));
    }
    public void start(){
        while(true){
            System.out.print("명령어: ");
            String command=DesignFactory.getScanner().nextLine().trim();
            if(command.length()==0){
                continue;
            }else if(command.equals("exit")){
                break;
            }

            RequestUtil request=new RequestUtil(command);
            if(request.isValidRequest()==false){
                continue;
            }

            if(controllers.containsKey(request.getControllerName())==false){
                continue;
            }

            controllers.get(request.getControllerName()).doAction(request);
        }
        DesignFactory.getDbConnection().close();
        DesignFactory.getScanner().close();
    }
}
