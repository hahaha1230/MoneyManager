package com.example.a25467.moneymanager.bean;

import java.util.List;

/**
 * Created by 25467 on 2018/2/11.
 */

public class HomeNewsWeather {
    private String city_name;
    private String city_id;
    private String last_update;
    private HomeNewsNow now;
    private  HomeNewsToday today;
    private  HomeNewsFuture future;
    @Override
    public String toString(){
        return "HomeNewsWeather[city_name="+city_name+",city_id="+city_id+",last_update="+last_update
        +",now="+now+",today="+today+",future="+future+"]";
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }

    public HomeNewsNow getNow() {
        return now;
    }

    public void setNow(HomeNewsNow now) {
        this.now = now;
    }

    public HomeNewsToday getToday() {
        return today;
    }

    public void setToday(HomeNewsToday today) {
        this.today = today;
    }

    public HomeNewsFuture getFuture() {
        return future;
    }

    public void setFuture(HomeNewsFuture future) {
        this.future = future;
    }
}
