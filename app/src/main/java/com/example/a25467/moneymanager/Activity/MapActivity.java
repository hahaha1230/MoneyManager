package com.example.a25467.moneymanager.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.a25467.moneymanager.R;

import java.util.ArrayList;
import java.util.List;

public class MapActivity extends Activity implements LocationSource,AMapLocationListener{
    MapView mMapView = null;
    //MapView mMapView;
    AMap aMap;
    //private boolean isFirstLocate=true;

    //定位所需要的数据
    LocationSource.OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    //定位蓝点
    MyLocationStyle myLocationStyle;
    public TextView locationDisplay;
    public boolean isFirstLoc=true;


   /* public AMapLocationClient mLocationClient = null;
    private TextView positionText;
    public AMapLocationListener mLocationListener;//= new AMapLocationListener();
    //声明Amaplocationclientoption对象
    public AMapLocationClientOption mLocationOption = null;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_map);
       locationDisplay=(TextView)findViewById(R.id.locationDisplay);

       List<String>permissionList=new ArrayList<>();
       if (ContextCompat.checkSelfPermission(MapActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
               != PackageManager.PERMISSION_GRANTED){
           permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
       }
       if (ContextCompat.checkSelfPermission(MapActivity.this,Manifest.permission.READ_PHONE_STATE)
               !=PackageManager.PERMISSION_GRANTED){
           permissionList.add(Manifest.permission.READ_PHONE_STATE);
       }
       if (ContextCompat.checkSelfPermission(MapActivity.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
               !=PackageManager.PERMISSION_GRANTED){
           permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
       }
       if (!permissionList.isEmpty()){
           String[]permissions=permissionList.toArray(new String[permissionList.size()]);
           ActivityCompat.requestPermissions(MapActivity.this,permissions,1);
       }
       else {
          // mlocationClient.startLocation();
       }


        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        //定义了一个地图view
        mMapView = (MapView) findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
//初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            UiSettings settings=aMap.getUiSettings();
            //设置定位监听
            aMap.setLocationSource(this);
        //设置为true表示显示定位层并触发定位，false表示隐藏定位层并不可触发，默认为false
          aMap.setMyLocationEnabled(true);
          //设置地图缩放级别
            aMap.moveCamera(CameraUpdateFactory.zoomTo(12));


            //设置定位的类型为定位模式
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        }



        //蓝点初始化
        myLocationStyle=new MyLocationStyle();//初始化定位蓝点样式类
        myLocationStyle.interval(10000);//设置定位间隔，单位为毫秒
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的style
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);//设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位
       myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//只在第一次定位移动到地图中心点

        myLocationStyle.showMyLocation(true);

        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                //从location对象中获取经纬度信息，地址描述信息，建议拿到位置后调用逆地理编码接口获取
            }
        });




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
                    mlocationClient.startLocation();
                    //调用显示
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
        mMapView.onDestroy();
        if (null !=mlocationClient){
            mlocationClient.onDestroy();
        }
    }
    @Override
    protected  void  onResume(){
        super.onResume();
        //在activity执行onresume时执行mmapview的onresume
        mMapView.onResume();
    }
    @Override
    protected  void onPause(){
        super.onPause();
        mMapView.onPause();
    }
    @Override
    protected  void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    //---定位监听---------
    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener){
        mListener=onLocationChangedListener;
        if (mlocationClient==null){
            //初始化定位
            mlocationClient=new AMapLocationClient(this);
            //初始化定位参数
            mLocationOption=new AMapLocationClientOption();
            //设置定位回调监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            //此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗
            //注意设置合适的定位时间间隔（最小间隔为2000ms），并且在合适的时间调用stoplocation（）方法来取消定位请求
            //在单次定位的情况下，定位无论成功与否，都无需调用stoplocation（）方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();//启动定位
        }
    }
    /**
     * 停止定位
     */
    @Override
    public  void  deactivate(){
        mListener=null;
        if (mlocationClient !=null){
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient=null;
    }


    //定位回调，在回调方法中调用‘mListener.onLocationChanged(amapLocation);'可以在地图上显示系统小蓝点
    @Override
    public void onLocationChanged(AMapLocation aMapLocation){
        if (mListener !=null&&aMapLocation !=null){
            if (aMapLocation !=null&& aMapLocation.getErrorCode()==0){
                mListener.onLocationChanged(aMapLocation);//显示系统小蓝点
                aMapLocation.getLocationType();//获取定位结果来源
                aMapLocation.getLatitude();//获取纬度信息
                aMapLocation.getLongitude();//获取经度信息
                aMapLocation.getAccuracy();//获取精度信息
                aMapLocation.getAddress();//地址
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省份信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getCityCode();//城区编码
                aMapLocation.getAdCode();//城区编码
                aMapLocation.getAoiName();//获取当前定位点AOI信息
                aMapLocation.getFloor();//获取当前定位楼层
                aMapLocation.getGpsAccuracyStatus();//获取当前GPS状态
                locationDisplay.setText(aMapLocation.getAddress());
               String m=aMapLocation.getAddress();
                Toast.makeText(this,m,Toast.LENGTH_SHORT).show();
                if (isFirstLoc) {

                    //获取定位信息
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(aMapLocation.getCountry() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getCity() + ""
                            + aMapLocation.getProvince() + ""
                            + aMapLocation.getDistrict() + ""
                            + aMapLocation.getStreet() + ""
                            + aMapLocation.getStreetNum());
                    Toast.makeText(getApplicationContext(), buffer.toString(), Toast.LENGTH_LONG).show();
                    isFirstLoc = false;
                }

            }
            else {
                String errText="定位失败,"+aMapLocation.getErrorCode()+":"+aMapLocation.getErrorInfo();
                Log.d("hhh",errText);
            }
        }
    }
   

}
