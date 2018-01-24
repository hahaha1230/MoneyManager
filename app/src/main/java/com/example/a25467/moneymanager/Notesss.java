package com.example.a25467.moneymanager;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/22.
 */

public class Notesss {
    private String notes;
    private String date;
    private long create_time;
    public Notesss(String notes, String date ,long create_time){
        this.date=date;
        this.notes=notes;
        this.create_time=create_time;
    }
    public long getCreate_time(){
        return create_time;
    }

    public String getNotes() {
        return notes;
    }


    public String getDate() {
        return date;
    }




}
