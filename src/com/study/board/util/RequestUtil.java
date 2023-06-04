package com.study.board.util;

public class RequestUtil {
    private String requestStr;
    private String controllerName;
    private String actionName;
    private String arg1;
    private String arg2;
    private String arg3;

    public boolean isValidRequest(){
        return actionName!=null;
    }
    public RequestUtil(String requestStr) {
        this.requestStr = requestStr;
        String[] requestStrBits=requestStr.split(" ");
        this.controllerName=requestStrBits[0];
        if(requestStrBits.length>1){
            this.actionName=requestStrBits[1];
        }
        if(requestStrBits.length>2){
            this.arg1=requestStrBits[2];
        }
        if(requestStrBits.length>3){
            this.arg2=requestStrBits[3];
        }
        if(requestStrBits.length>4){
            this.arg3=requestStrBits[4];
        }
    }

    public String getControllerName() {
        return controllerName;
    }

    public void setControllerName(String controllerName) {
        this.controllerName = controllerName;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getArg1() {
        return arg1;
    }

    public void setArg1(String arg1) {
        this.arg1 = arg1;
    }

    public String getArg2() {
        return arg2;
    }

    public void setArg2(String arg2) {
        this.arg2 = arg2;
    }

    public String getArg3() {
        return arg3;
    }

    public void setArg3(String arg3) {
        this.arg3 = arg3;
    }
}
