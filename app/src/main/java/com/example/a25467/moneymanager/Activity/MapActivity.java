package com.example.a25467.moneymanager.Activity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.a25467.moneymanager.Class.GetContext;
import com.example.a25467.moneymanager.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity implements AMapLocationListener,AMap.OnMapClickListener {
    private MapView mapView;
    private AMap aMap;
    private AMapLocationClient mLocationClient;
    private Marker centerMarker;
    private TextView tvResult;
    private Button locateSure;
    private Button locateQuit;
    private ProgressDialog dialog;
    private boolean isLocation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        List<String>permissionList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(GetContext.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(GetContext.getContext(),Manifest.permission.READ_PHONE_STATE)
                !=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(GetContext.getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)
                !=PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[]permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(MapActivity.this,permissions,1);
        }
        initview();
        mapView.onCreate(savedInstanceState);
        initMap();
        startLocation();


    }
    @Override
    public void onRequestPermissionsResult(int requestCode,String[]permissions,int[]grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result !=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有的权限才能使用该功能",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                }
                else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        //在activity被销毁的时候也销毁mmapview
        stopLocation();
        mapView.onDestroy();
    }
    @Override
    protected  void  onResume(){
        super.onResume();
        //在activity执行onresume时执行mmapview的onresume
        mapView.onResume();
    }
    @Override
    protected  void onPause(){
        super.onPause();
        mapView.onPause();

    }

    /**
     * 初始化控件
     */
    private  void initview(){
        mapView=findViewById(R.id.map);
        tvResult=findViewById(R.id.tvresult);
        locateQuit=findViewById(R.id.locate_quit);
        locateSure=findViewById(R.id.locate_sure);
        tvResult.setText("正在定位中，请稍后。。。。。");
        locateSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tvResult.getText()!=""){
                    Intent intent=new Intent();
                    intent.putExtra("result",tvResult.getText());
                    setResult(RESULT_OK,intent);
                    finish();
                }
                else {
                    Toast.makeText(GetContext.getContext(),"请选择地点。",Toast.LENGTH_SHORT).show();
                }
            }
        });
        locateQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    //点击back时能返回数据
    @Override
    public void onBackPressed(){
        Intent intent=new Intent();
        intent.putExtra("result",tvResult.getText());
        setResult(RESULT_OK,intent);
        finish();
    }
    /**
     * 初始化地图
     */
    public  void initMap(){
        aMap=mapView.getMap();
        MyLocationStyle myLocationStyle=new MyLocationStyle();
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
        myLocationStyle.interval(2000);
        aMap.setMyLocationStyle(myLocationStyle);

        UiSettings mUiSettings=aMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);
        mUiSettings.setCompassEnabled(true);
        mUiSettings.setScaleControlsEnabled(true);
        mUiSettings.setMyLocationButtonEnabled(true);
        aMap.setMyLocationEnabled(true);
    }

    /**
     * 开启定位
     */
    public void  startLocation(){
        mLocationClient=new AMapLocationClient(getApplicationContext());
        mLocationClient.setLocationListener(this);
        AMapLocationClientOption option=new AMapLocationClientOption();
        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        option.setLocationCacheEnable(false);
        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }
    /**
     * 停止定位
     */
    private void stopLocation(){
        if (mLocationClient !=null){
            mLocationClient.stopLocation();
        }
    }
    @Override
    public void onLocationChanged(AMapLocation aMapLocation){
        if (aMapLocation !=null && aMapLocation.getErrorCode()==0){
            if (!isLocation){
                isLocation=true;
                setMapCenter(new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude()));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                aMap.setOnMapClickListener(this);
                tvResult.setText(aMapLocation.getAddress());

            }
        }
        else {
            String errText="定位失败"+aMapLocation.getErrorCode()+":"+aMapLocation.getErrorInfo();
            Log.d("hhh",errText);
        }
    }
    /**
     * 地图点击事件
     */
    @Override
    public void onMapClick(LatLng latLng){
        setMapCenter(latLng);
        getAddressInfo(latLng);
    }
    /**
     * 设置地图当前显示的中心点
     */
    private void setMapCenter(LatLng latLng){
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(latLng));
        if (centerMarker !=null){
            centerMarker.setPositionNotUpdate(latLng);
        }
        else {
            MarkerOptions markerOptions=new MarkerOptions().icon
                    (BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            markerOptions.position(latLng);
            centerMarker=aMap.addMarker(markerOptions);
        }
    }
    /**
     * 获取具体的位置信息
     */
    private void  getAddressInfo(final  LatLng latLng){
        if (dialogIsShow()) {
            return;
        }
        showDialog();
        //开启线程请求位置信息
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Geocoder geocoder=new Geocoder(GetContext.getContext(), Locale.getDefault());
                    List addresses=geocoder.getFromLocation(latLng.latitude,latLng.longitude,1);
                    if (addresses.size()>0){
                        String addressInfo=addresses.get(0).toString();
                        int startCity=addressInfo.indexOf("locality=")+"locality=".length();
                        int endCity=addressInfo.indexOf(",",startCity);
                        String city=addressInfo.substring(startCity,endCity);
                        int startPlace=addressInfo.indexOf("feature=")+"feature=".length();

                        int endPlace=addressInfo.indexOf(",",startPlace);

                        String place=addressInfo.substring(startPlace,endPlace);
                        Message msg= handler.obtainMessage();
                        msg.what=0;
                        msg.obj=city+place;
                        handler.sendMessage(msg);
                    }
                }
                catch (IOException e){
                    e.printStackTrace();
                    Message msg=handler.obtainMessage();
                    msg.what=0;
                    msg.obj="查询出错"+e.getMessage();
                    handler.sendMessage(msg);
                }
            }
        }).start();

    }
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what==0){
                dismiss();
                String addressInfo=(String)msg.obj;
                tvResult.setText(addressInfo);
            }
            return true;
        }
    });
    private void  showDialog(){
        if (dialog==null){
            dialog=new ProgressDialog(this);
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("获取位置信息中...");
        }
        dialog.show();
    }
    /**
     * 消失dialog
     */
    private void dismiss(){
        if (dialogIsShow()){
            dialog.dismiss();
        }
    }
    private boolean dialogIsShow(){
        return dialog !=null&&dialog.isShowing();
    }
}
