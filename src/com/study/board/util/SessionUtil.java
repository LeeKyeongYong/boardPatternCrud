package com.study.board.util;

import com.study.board.model.BoardDTO;

public class SessionUtil {
    //session을 사용하는 util
    //현재 사용자가 이용중인 정보
    //이 안의 정보는 사용자가 프로그램을 사용할때 계속 유지된다.
    private BoardDTO currentBoard;

    public BoardDTO getCurrentBoard() {
        return currentBoard;
    }

    public void setCurrentBoard(BoardDTO currentBoard) {
        this.currentBoard = currentBoard;
    }
}
