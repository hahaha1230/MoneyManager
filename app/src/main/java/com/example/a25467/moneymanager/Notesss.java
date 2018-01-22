package com.example.a25467.moneymanager;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/22.
 */

public class Notesss {
    private String notes;
    private String date;
    public Notesss(String notes, String date){
        this.date=date;
        this.notes=notes;
    }

    public String getNotes() {
        return notes;
    }


    public String getDate() {
        return date;
    }




}
