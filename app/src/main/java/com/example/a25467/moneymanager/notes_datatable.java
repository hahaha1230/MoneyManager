package com.example.a25467.moneymanager;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/19.
 */

public class notes_datatable extends DataSupport{
    private String date;
    private  String content;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
