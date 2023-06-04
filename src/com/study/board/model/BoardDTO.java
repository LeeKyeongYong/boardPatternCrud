package com.study.board.model;

import java.util.Map;

public class BoardDTO extends ModelDTO{
    private String name;
    private String code;

    public BoardDTO() {
    }

    public BoardDTO(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public BoardDTO(Map<String, Object> row) {
        super(row);
        this.code=(String)row.get("code");
        this.name=(String)row.get("name");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
