package com.study.board.controller;

import com.study.board.pattern.DesignFactory;
import com.study.board.service.BuildServcie;
import com.study.board.util.MasterUtil;
import com.study.board.util.RequestUtil;

public class BuildController  extends MasterUtil {
    private BuildServcie buildService;

    public BuildController() {
        this.buildService = DesignFactory.getBuildServcie();
    }

    @Override
    public void doAction(RequestUtil request) {
        if(request.getActionName().equals("site")){
            actionSite(request);
        }
    }

    private void actionSite(RequestUtil request){
        buildService.buildSite();
    }
}
