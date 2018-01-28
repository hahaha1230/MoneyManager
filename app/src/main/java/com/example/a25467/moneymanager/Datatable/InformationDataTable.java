package com.example.a25467.moneymanager.Datatable;

import org.litepal.crud.DataSupport;

/**
 * Created by 25467 on 2018/1/28.
 */

public class InformationDataTable extends DataSupport {
    private String name;
    private String sex;
    private Double budget_Pay;
    private Double budget_Income;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Double getBudget_Pay() {
        return budget_Pay;
    }

    public void setBudget_Pay(Double budget_Pay) {
        this.budget_Pay = budget_Pay;
    }

    public Double getBudget_Income() {
        return budget_Income;
    }

    public void setBudget_Income(Double budget_Income) {
        this.budget_Income = budget_Income;
    }
}
