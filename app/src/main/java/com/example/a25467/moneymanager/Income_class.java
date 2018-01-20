package com.example.a25467.moneymanager;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/20.
 */

public class Income_class extends DataSupport {
    private int money;
    private String date;
    private String category;
    private String account;
    private String notes;

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
