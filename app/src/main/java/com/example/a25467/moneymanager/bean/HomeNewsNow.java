package com.example.a25467.moneymanager.bean;

/**
 * Created by 25467 on 2018/2/11.
 */

public class HomeNewsNow {
    private String text;
    private String code;
    private String temperature;
    private String feels_like;
    private String wind_direction;
    private String wind_speed;
    private String wind_sacle;
    private String humidity;
    private String visibility;
    private String pressure;
    private String pressure_rising;
    private HomeNewsAirQuality air_quality;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(String feels_like) {
        this.feels_like = feels_like;
    }

    public String getWind_direction() {
        return wind_direction;
    }

    public void setWind_direction(String wind_direction) {
        this.wind_direction = wind_direction;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_sacle() {
        return wind_sacle;
    }

    public void setWind_sacle(String wind_sacle) {
        this.wind_sacle = wind_sacle;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getPressure_rising() {
        return pressure_rising;
    }

    public void setPressure_rising(String pressure_rising) {
        this.pressure_rising = pressure_rising;
    }

    public HomeNewsAirQuality getAir_quality() {
        return air_quality;
    }

    public void setAir_quality(HomeNewsAirQuality air_quality) {
        this.air_quality = air_quality;
    }

    @Override
    public String toString() {
        return "HomeNewsNow{" +
                "text='" + text + '\'' +
                ", code='" + code + '\'' +
                ", temperature='" + temperature + '\'' +
                ", feels_like='" + feels_like + '\'' +
                ", wind_direction='" + wind_direction + '\'' +
                ", wind_speed='" + wind_speed + '\'' +
                ", wind_sacle='" + wind_sacle + '\'' +
                ", humidity='" + humidity + '\'' +
                ", visibility='" + visibility + '\'' +
                ", pressure='" + pressure + '\'' +
                ", pressure_rising='" + pressure_rising + '\'' +
                ", air_quality=" + air_quality +
                '}';
    }
}
