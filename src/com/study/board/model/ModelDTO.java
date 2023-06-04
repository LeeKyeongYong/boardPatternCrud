package com.study.board.model;

import java.util.HashMap;
import java.util.Map;
import com.study.board.util.DfldUtil;

public abstract class ModelDTO {
    private int id;
    private String WriteDate;
    private Map<String,Object> extra;

    public ModelDTO() {
        this(0);
    }

    public ModelDTO(int id) {
        this(id,DfldUtil.getNowDateStr());
    }

    public ModelDTO(int id, String writeDate) {
       this(id,writeDate,new HashMap<>());
    }

    public ModelDTO(int id, String writeDate, Map<String, Object> extra) {
        this.id = id;
        this.WriteDate = writeDate;
        this.extra = extra;
    }


    public ModelDTO(Map<String, Object> row) {
        this((int)row.get("id"),(String)row.get("writeDate"));
        for(String key : row.keySet()){
            if(key.startsWith("extra__")){
                String extraKey= key.replace("extra__","");
                Object extraValue=row.get(key);
                extra.put(extraKey,extraValue);
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getWriteDate() {
        return WriteDate;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWriteDate(String writeDate) {
        WriteDate = writeDate;
    }

    public void setExtra(Map<String, Object> extra) {
        this.extra = extra;
    }
}
