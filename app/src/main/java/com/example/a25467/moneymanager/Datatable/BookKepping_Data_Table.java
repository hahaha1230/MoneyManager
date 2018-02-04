package com.example.a25467.moneymanager.Datatable;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/23.
 */

public class BookKepping_Data_Table extends DataSupport {
   private Double money;
   private long date;
   private String account;
   private String notes;
   private int category;
   private String source_or_purpose;
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

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
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
