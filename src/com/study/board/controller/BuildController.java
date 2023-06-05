package com.study.board.controller;

import com.study.board.service.BuildServcie;
import com.study.board.util.MasterUtil;
import com.study.board.util.RequestUtil;

public class BuildController  extends MasterUtil {
    private BuildServcie buildService;

    public BuildController(BuildServcie buildService) {
        this.buildService = buildService;
    }

    @Override
    public void doAction(RequestUtil request) {
        if(request.getActionName().equals("site")){
            actionSite(request);
        }
    }

    private void actionSite(RequestUtil request){
        buildService.buildsidte();
    }
}
