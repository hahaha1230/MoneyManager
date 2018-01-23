package com.example.a25467.moneymanager;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/23.
 */

public class BookKepping_Data_Table extends DataSupport {
   private Double money;
   private Long date;
   private String account;
   private String notes;
   private int category;
   private String source_or_purpose;

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Long getDate() {
        return date;
    }

    public void setDate(Long date) {
        this.date = date;
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getSource_or_purpose() {
        return source_or_purpose;
    }

    public void setSource_or_purpose(String source_or_purpose) {
        this.source_or_purpose = source_or_purpose;
    }
}
