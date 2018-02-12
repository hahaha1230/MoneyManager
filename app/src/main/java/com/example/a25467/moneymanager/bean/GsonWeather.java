package com.example.a25467.moneymanager.bean;

import java.util.List;

/**
 * Created by 25467 on 2018/2/10.
 */

public class GsonWeather {
   private String status;
   private List<HomeNewsWeather>weather;



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<HomeNewsWeather> getWeather() {
        return weather;
    }

    public void setWeather(List<HomeNewsWeather> weather) {
        this.weather = weather;
    }

    @Override
    public String toString(){
        return "GsonWeather[status="+status+",weather="+weather+"]";
    }
}
