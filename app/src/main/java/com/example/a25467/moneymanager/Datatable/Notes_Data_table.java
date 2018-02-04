package com.example.a25467.moneymanager.Datatable;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/23.
 */

public class Notes_Data_table extends DataSupport {
    private String content;
    private long date;
    private long create_time;
    private String locate;

    public String getLocate() {
        return locate;
    }

    public void setLocate(String locate) {
        this.locate = locate;
    }

    public long getCreate_time() {
        return create_time;
    }

    public void setCreate_time(long create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }
}
