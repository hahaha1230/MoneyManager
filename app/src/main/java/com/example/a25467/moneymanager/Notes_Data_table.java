package com.example.a25467.moneymanager;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/23.
 */

public class Notes_Data_table extends DataSupport {
    private String content;
    private String date;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
