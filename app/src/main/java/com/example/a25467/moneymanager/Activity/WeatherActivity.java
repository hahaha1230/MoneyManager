package com.example.a25467.moneymanager.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25467.moneymanager.bean.GsonWeather;
import com.example.a25467.moneymanager.R;
import com.example.a25467.moneymanager.bean.HomeNewsNow;
import com.example.a25467.moneymanager.manager.DbManager;
import com.example.a25467.moneymanager.utils.HttpUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class WeatherActivity extends AppCompatActivity {
    private Handler handler;
    private EditText editText;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView cityname;
    private TextView time;
    private TextView weatherConditions1;
    private TextView temerature1;
    private TextView feel_like1;
    private TextView wind_direction1;
    private TextView wind_speed1;
    private TextView wind_scale1;
    private TextView humidity1;
    private TextView visibility1;
    private TextView quarity1;
    private TextView pressure1;
    private TextView pm2_51;
    private TextView pm10_1;
    private TextView so2_1;
    private TextView no2_1;
    private TextView co_1;
    private TextView o3_1;
    private TextView dressing1;
    private TextView uv_information1;
    private TextView car_washing_information1;
    private TextView travel_information1;
    private TextView flu_information1;
    private TextView sport_information1;
    private TextView future1, future2, future3;
    private LocationManager locationManager;
    private String provider;
    public Double latitude;
    public Double longitude;
    public String cityName;
    public boolean isFirstOpen = true;
    public boolean refrashGetLocate = false;
    public String city_name;
    public String weatherConditions;
    public String temperature;
    public String feel_like;
    public String wind_direction;
    public String wind_speed;
    public String wind_scale;
    public String humidity;
    public String visibility;
    public String pressure;
    public String aqi;
    public String pm2_5;
    public String PM10;
    public String so2;
    public String no2;
    public String co;
    public String o3;
    public String quality;
    public String sunrise;
    public String sunset;
    public String dressing_information;
    public String uv_information;
    public String car_washing_information;
    public String traver_information;
    public String flu_information;
    public String sport_information;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        initview();


        /**
         *  检查是否有权限
         */

        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(WeatherActivity.this, permissions, 1);
        }

        location();
        /**
         *  若用户没有打开GPS，提示用户打开
         */

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && !locationManager.isProviderEnabled(LocationManager
                .NETWORK_PROVIDER)) {
            // Toast.makeText(WeatherActivity.this,"请打开GPS",Toast.LENGTH_SHORT).show();
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("请打开GPS连接");
            dialog.setMessage("为了能给您发送天气信息，需要先判断您的位置，请先打开GPS。");
            dialog.setPositiveButton("设置", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    //转到手机设置界面，用户设置GPS
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    Toast.makeText(WeatherActivity.this, "打开后直接点击返回键下拉刷新即可切换到所在城市"
                            , Toast.LENGTH_SHORT).show();
                    //设置完成后返回原来界面
                    startActivityForResult(intent, 0);
                }

            });
            dialog.setNeutralButton("取消", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface arg0, int arg1) {
                    arg0.dismiss();
                }
            });
            dialog.show();

        }
        /**
         * 获得城市名
         */
        try {
            cityName = getAddress(latitude, longitude);
            // 将城市名后面的市去掉
            cityName = cityName.substring(0, cityName.length() - 1);
            Log.d("hhh", cityName);

        } catch (Exception e) {
            e.printStackTrace();
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Toast.makeText(WeatherActivity.this, "获取失败,请走到开放地带！", Toast.LENGTH_SHORT).show();
            }
        }
        if (isFirstOpen) {
            editText.setText(cityName);
            deal();
            isFirstOpen = false;
        }
        initHandler();
    }

    /**
     * 初始化控件
     */

    public void initview() {
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh1);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();//调用刷新方法
            }
        });
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deal();
            }
        });
        editText = (EditText) findViewById(R.id.edit_text);
        cityname = findViewById(R.id.cityname);
        time = findViewById(R.id.time);
        weatherConditions1 = findViewById(R.id.weather_conditions);
        temerature1 = findViewById(R.id.temperature);
        feel_like1 = findViewById(R.id.feel_like);
        wind_direction1 = findViewById(R.id.wind_direction);
        wind_speed1 = findViewById(R.id.wind_speed);
        wind_scale1 = findViewById(R.id.wind_scale);
        humidity1 = findViewById(R.id.humidity);
        visibility1 = findViewById(R.id.visibility);
        quarity1 = findViewById(R.id.quality);
        pressure1 = findViewById(R.id.pressure);
        future1 = findViewById(R.id.future1);
        future2 = findViewById(R.id.future2);
        future3 = findViewById(R.id.future3);
        pm2_51 = findViewById(R.id.pm_2_5);
        pm10_1 = findViewById(R.id.pm_10);
        so2_1 = findViewById(R.id.so2);
        no2_1 = findViewById(R.id.no2);
        co_1 = findViewById(R.id.co);
        o3_1 = findViewById(R.id.o3);
        dressing1 = findViewById(R.id.dressing);
        car_washing_information1 = findViewById(R.id.car_washing_information);
        uv_information1 = findViewById(R.id.uv_information);
        travel_information1 = findViewById(R.id.travel_information);
        flu_information1 = findViewById(R.id.flu_information);
        sport_information1 = findViewById(R.id.sport_information);

    }


    /**
     * 获取经纬度
     */
    public void location() {
        //获取定位服务
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //获取当前可用的位置控制器
        List<String> list = locationManager.getProviders(true);
        //是否为GPS位置控制器
        if (list.contains(LocationManager.GPS_PROVIDER)) {
            provider = LocationManager.GPS_PROVIDER;
        }
        // 是否为网络位置控制器
        else if (list.contains(LocationManager.NETWORK_PROVIDER)) {
            provider = LocationManager.NETWORK_PROVIDER;
        } else {
            if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) && locationManager.isProviderEnabled(LocationManager
                    .NETWORK_PROVIDER)) {
                Toast.makeText(this, "定位失败！", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        Log.d("hhh", "zzzzz");
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(WeatherActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(WeatherActivity.this, permissions, 1);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            //获取当前位置
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d("hhh", String.valueOf(latitude) + "11111");
            Log.d("hhh", String.valueOf(longitude) + "11111");
            refrashGetLocate = true;
        } else {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, locationListener);
            Location location1 = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            if (location != null) {
                latitude = location1.getLatitude();
                longitude = location1.getLongitude();
                refrashGetLocate = true;
            } else {
                refrashGetLocate = false;
            }
        }
        locationManager.requestLocationUpdates(provider, 1000, 2, locationListener);

    }

    /**
     * 回调授权结果
     * @param requestCode
     * @param permissions
     * @param grantResults
     */

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意权限才能使用该功能", Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    /**
     *   将经纬度信息转换成城市信息
     */
    public String getAddress(double latitude, double longitude) {
        Log.d("hhh", "111");
        Geocoder geocoder = new Geocoder(WeatherActivity.this, Locale.getDefault());
        try {
            Log.d("hhh", "112");
            List addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                String data = addresses.get(0).toString();
                Log.d("hhh", "113");
                int startCity = data.indexOf("locality=") + "locality=".length();
                int endCity = data.indexOf(",", startCity);
                String city = data.substring(startCity, endCity);
                Log.d("hhh", city);
                return city;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "获取失败";
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }

    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }

    /**
     * 发送城市天气请求，获得结果
     */

    private void deal() {
        final String cityStr = editText.getText().toString();
        if (cityStr.isEmpty() && (!isFirstOpen)) {
            Toast.makeText(this, "请输入城市！", Toast.LENGTH_SHORT).show();
        } else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String code = DbManager.get().getCityCode(WeatherActivity.this, cityStr);
                    if (code == null || code.isEmpty()) {
                        handler.sendEmptyMessage(1);
                    } else {
                        String url = HttpUtils.getUrl(code);
                        String result = HttpUtils.get(url);
                        if (result == null) {
                            handler.sendEmptyMessage(2);
                        } else {
                            //result 就是返回的东西
                            String model = CityWeatherModel(result);
                            CityWeatherModel(result);
                            if (model == null) {
                                handler.sendEmptyMessage(2);
                            } else {
                                handler.sendEmptyMessage(0);
                            }
                        }
                    }
                }
            }).start();
        }
    }


    /**
     * 解析json数据，并把解析出来的数据赋值给变量
     * @param json
     * @return
     */
    public String CityWeatherModel(String json) {
        parseJSONWithGSON(json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String status = jsonObject.optString("status");
            if (!status.equals("OK")) {
                return null;
            }
            //城市名
            JSONArray jsonArray = jsonObject.optJSONArray("weather");
            jsonObject = jsonArray.optJSONObject(0);
            city_name = jsonObject.optString("city_name");
            JSONObject now = jsonObject.optJSONObject("now");
            //天气状况
            weatherConditions = now.optString("text");
            //当前温度
            temperature = now.optString("temperature");
            //体感温度
            feel_like = now.optString("feels_like");
            //风向
            wind_direction = now.optString("wind_direction");
            //风速
            wind_speed = now.optString("wind_speed");
            //风力大小
            wind_scale = now.optString("wind_scale");
            //空气湿度
            humidity = now.optString("humidity");
            //能见度，单位为km
            visibility = now.optString("visibility");
            //气压，单位为hpa
            pressure = now.optString("pressure");
            //空气质量指数
            JSONObject air_quality = now.optJSONObject("air_quality");
            JSONObject city = air_quality.optJSONObject("city");
            aqi = city.optString("aqi");
            //pm2.5指数
            pm2_5 = city.optString("pm25");
            //pm10指数
            PM10 = city.optString("pm10");
            //二氧化硫指数
            so2 = city.optString("so2");
            //二氧化氮指数
            no2 = city.optString("no2");
            //一氧化碳指数
            co = city.optString("co");
            //臭氧指数
            o3 = city.optString("o3");
            //空气质量
            quality = city.optString("quality");
            //日出时间
            JSONObject today = jsonObject.optJSONObject("today");
            sunrise = today.optString("sunrise");
            //日落时间
            sunset = today.optString("sunset");
            //穿衣信息
            JSONObject suggestion = today.optJSONObject("suggestion");
            JSONObject dressing = suggestion.optJSONObject("dressing");
            dressing_information = dressing.optString("brief") + "  " + dressing.optString("details");
            //紫外线信息
            JSONObject uv = suggestion.optJSONObject("uv");
            uv_information = uv.optString("brief") + "  " + uv.optString("details");
            //洗车信息
            JSONObject car_washing = suggestion.optJSONObject("car_washing");
            car_washing_information = car_washing.optString("brief") + "  " + car_washing.optString("details");
            //旅游信息
            JSONObject travel = suggestion.optJSONObject("travel");
            traver_information = travel.optString("brief") + "  " + travel.optString("details");
            //流感信息
            JSONObject flu = suggestion.optJSONObject("flu");
            flu_information = flu.optString("brief") + "  " + flu.optString("details");
            //运动信息
            JSONObject sport = suggestion.optJSONObject("sport");
            sport_information = sport.optString("brief") + "  " + sport.optString("details");
            //未来天气状况表
            try {
                JSONArray jsonArray1 = jsonObject.optJSONArray("future");
                for (int i = 0; i < 3; i++) {
                    JSONObject jsonObject1 = jsonArray1.optJSONObject(i);
                    String date = jsonObject1.optString("date");
                    String low = jsonObject1.optString("low");
                    String high = jsonObject1.optString("high");
                    String day = jsonObject1.optString("day");
                    String wind = jsonObject1.optString("code2");
                    if (i == 1) {
                        future1.setText("明日" + day + "最低温" + low + "℃" + "最高温" + high + "℃风力" + wind + "级");
                    } else if (i == 2) {
                        future2.setText("后日" + day + "最低温" + low + "℃" + "最高温" + high + "℃风力" + wind + "级");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return PM10;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 利用Gson解析json数据
     * @param jsonData
     */
    private void parseJSONWithGSON(String jsonData) {
        Gson gson=new Gson();
       // GsonWeather  gsonWeather=gson.fromJson(jsonData,GsonWeather.class);
       // Log.d("hhhh",gsonWeather.getStatus());
       // HomeNewsNow homeNewsNow=gson.fromJson(jsonData,HomeNewsNow.class);
       // Log.d("hhhh",homeNewsNow.getHumidity());

       /* JsonObject jsonObject = new JsonParser().parse(jsonData).getAsJsonObject();
        //加上数据头
        JsonArray jsonArray = jsonObject.getAsJsonArray("weather");
        Gson gson = new Gson();
        ArrayList<GsonWeather.Bean> beanArrayList = new ArrayList<>();
        for (JsonElement weather : jsonArray) {
            GsonWeather.Bean userBean = gson.fromJson(weather, new TypeToken<GsonWeather.Bean>() {
            }.getType());
            beanArrayList.add(userBean);
            Log.d("hhh", String.valueOf(userBean.getCity_name() + "hhhhh"));
            Log.d("hhh", String.valueOf(userBean.getCity_id() + "hhhhh"));
            Log.d("hhh", String.valueOf(userBean.getLast_update() + "hhhhh"));
            JsonObject jsonObject1 = jsonArray.getAsJsonObject();
        }
        Log.d("hhh", "hhhhh" + String.valueOf(beanArrayList));
        /* List <GsonWeather.Bean>beanList=gson.fromJson(jsonData,new TypeToken<List<GsonWeather.Bean>>(){}.getType());
        for (GsonWeather.Bean bean:beanList){
            Log.d("hhh",bean.getCity_name()+"zzzzzz");
        }*/
       // GsonWeather gsonWeather = gson.fromJson(jsonData, GsonWeather.class);
        //Log.d("hhh", "status is" + gsonWeather.getStatus());

        //List<GsonWeather.Bean>beanList=gsonWeather.getData();

        // Log.d("hhh",String.valueOf(beanList)+"hhhhh");

    }

    /**
     * 将解析出来的数据展示出来
     */

    public void show() {
        cityname.setText(city_name + ":");
        weatherConditions1.setText(weatherConditions);
        temerature1.setText(temperature + "℃");
        feel_like1.setText(feel_like + "℃");
        wind_direction1.setText(wind_direction);
        wind_speed1.setText(wind_speed);
        wind_scale1.setText(wind_scale + "级");
        quarity1.setText(quality);
        humidity1.setText(humidity);
        visibility1.setText(visibility + "km");
        pressure1.setText(pressure + "hPa");
        pm2_51.setText(pm2_5);
        pm10_1.setText(PM10);
        so2_1.setText(so2);
        no2_1.setText(no2);
        co_1.setText(co);
        o3_1.setText(o3);
        dressing1.setText("穿衣信息：" + dressing_information);
        uv_information1.setText("紫外线信息：" + uv_information);
        car_washing_information1.setText("洗车信息：" + car_washing_information);
        travel_information1.setText("旅游信息：" + traver_information);
        flu_information1.setText("流感信息：" + flu_information);
        sport_information1.setText("运动信息：" + sport_information);
    }


    /**
     * 初始化handler
     * 规定 ：
     * 0 是 成功
     * 1 是 获取城市码失败
     * 2 是 查询失败
     */
    private void initHandler() {
        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message message) {
                switch (message.what) {
                    case 0:
                        show();
                        break;
                    case 1:
                        Toast.makeText(WeatherActivity.this, "您输入的城市不存在，请重新输入！", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(WeatherActivity.this, "查询失败！", Toast.LENGTH_SHORT).show();
                        break;
                }
                return true;
            }
        });
    }

    /**
     * 下拉刷新，获取所在的城市天气信息
     */
    private void refresh() {
        location();
        try {
            cityName = getAddress(latitude, longitude);
            // 将城市名后面的市去掉
            cityName = cityName.substring(0, cityName.length() - 1);
            Log.d("hhh", cityName);

        } catch (Exception e) {
            e.printStackTrace();
        }
        initHandler();

        if (refrashGetLocate == true) {
            editText.setText(cityName);
            deal();
            show();
            Toast.makeText(WeatherActivity.this, "刷新成功,已定位到您所在城市", Toast.LENGTH_SHORT).show();
            refrashGetLocate = false;
        } else if (refrashGetLocate == false) {
            Toast.makeText(WeatherActivity.this, "刷新失败，请走到开阔地带或将定位服务调成只使用网络定位状态。", Toast.LENGTH_SHORT).show();
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}
