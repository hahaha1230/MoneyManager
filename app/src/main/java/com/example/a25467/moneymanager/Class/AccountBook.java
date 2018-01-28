package com.example.a25467.moneymanager.Class;

/**
 * Created by 25467 on 2018/1/23.
 */

public class AccountBook {
    private  String brief;
    private  long create_time;
    public AccountBook(String brief,long create_time){
        this.brief=brief;
        this.create_time=create_time;
    }

    public String getBrief() {
        return brief;
    }
    public long getCreate_time(){
        return create_time;
    }
}
