package com.example.a25467.moneymanager.Activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.fence.GeoFence;
import com.amap.api.fence.GeoFenceListener;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.a25467.moneymanager.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MapActivity1 extends Activity implements GeoFenceListener,AMap.OnMapClickListener,
        LocationSource,AMapLocationListener{
    MapView mMapView = null;
    //MapView mMapView;
    AMap aMap;
    //View v1;

    //中心点坐标
    private LatLng centerLatLng=null;
    //中心点marker
    private Marker centerMarker;
    public String my_location;
    public  boolean isFirst=true;

    private BitmapDescriptor ICON_YELLOW= BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
    private BitmapDescriptor ICON_RED=BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
    private MarkerOptions markerOptions=null;
    private List<Marker>markerList=new ArrayList<Marker>();
    //private boolean isFirstLocate=true;

    //定位所需要的数据
    OnLocationChangedListener mListener;
    AMapLocationClient mlocationClient;
    AMapLocationClientOption mLocationOption;
    //定位蓝点
    MyLocationStyle myLocationStyle;
    public TextView locationDisplay;
    public TextView tvresult;
    private UiSettings mUiSettings;
    Button locate_sure,locate_quit;


   /* public AMapLocationClient mLocationClient = null;
    private TextView positionText;
    public AMapLocationListener mLocationListener;//= new AMapLocationListener();
    //声明Amaplocationclientoption对象
    public AMapLocationClientOption mLocationOption = null;*/



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_map);
       setTitle("高德地图");
        locate_sure=(Button)findViewById(R.id.locate_sure);
       locate_quit=(Button)findViewById(R.id.locate_quit);
       tvresult=(TextView)findViewById(R.id.tvresult);

       locate_sure.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (tvresult.getText()!=""){
                   Intent intent=new Intent();
                   intent.putExtra("result",tvresult.getText());
                   setResult(RESULT_OK,intent);
                   finish();
               }
           }
       });

        //setContentView(v1);
       //locationDisplay=(TextView)findViewById(R.id.locationDisplay);


       List<String>permissionList=new ArrayList<>();
       if (ContextCompat.checkSelfPermission(MapActivity1.this, Manifest.permission.ACCESS_FINE_LOCATION)
               != PackageManager.PERMISSION_GRANTED){
           permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
       }
       if (ContextCompat.checkSelfPermission(MapActivity1.this,Manifest.permission.READ_PHONE_STATE)
               !=PackageManager.PERMISSION_GRANTED){
           permissionList.add(Manifest.permission.READ_PHONE_STATE);
       }
       if (ContextCompat.checkSelfPermission(MapActivity1.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
               !=PackageManager.PERMISSION_GRANTED){
           permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
       }
       if (!permissionList.isEmpty()){
           String[]permissions=permissionList.toArray(new String[permissionList.size()]);
           ActivityCompat.requestPermissions(MapActivity1.this,permissions,1);
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
        markerOptions =new MarkerOptions().draggable(true);
        //初始化地图控制器对象
        if (aMap == null) {
            aMap = mMapView.getMap();
            mUiSettings = aMap.getUiSettings();
            aMap.getUiSettings().setRotateGesturesEnabled(false);
            aMap.moveCamera(CameraUpdateFactory.zoomBy(6));
            //设置定位监听
            aMap.setLocationSource(this);
            //设置为true表示显示定位层并触发定位，false表示隐藏定位层并不可触发，默认为false
            aMap.setMyLocationEnabled(true);
            //设置地图缩放级别
            aMap.moveCamera(CameraUpdateFactory.zoomTo(12));


            //设置定位的类型为定位模式
            aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
            setUpMap();
            //设置显示指北针
            mUiSettings.setCompassEnabled(true);
            //设置显示比例尺
            mUiSettings.setScaleControlsEnabled(true);
        }


    }
    //点击back时能返回数据
    @Override
    public void onBackPressed(){
        Intent intent=new Intent();
        intent.putExtra("result",tvresult.getText());
        setResult(RESULT_OK,intent);
        finish();
    }
    private void setUpMap(){
        //在子线程中执行操作
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message=new Message();
                message.what=UPDATE_MAP;
                handler.sendMessage(message);//将message对象发送出去
            }
        }).start();


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
        deactivate();
    }
    @Override
    protected  void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
    List<GeoFence>fencesList=new ArrayList<GeoFence>();
    @Override
    public void onGeoFenceCreateFinished(final List<GeoFence>geoFenceList,int errorCode,String customId){
        Message msg=Message.obtain();
        if (errorCode==GeoFence.ADDGEOFENCE_SUCCESS){
            fencesList=geoFenceList;
            msg.obj=customId;
            msg.what=0;
        }
        else {
            msg.arg1=errorCode;
            msg.what=1;
        }

    }
    public Double latitude;
    public Double longitude;

    @Override
    public void onMapClick(LatLng latLng){
        markerOptions.icon(ICON_RED);
        centerLatLng=latLng;
        //以点击点作为屏幕中心
        changeCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder().target(latLng).zoom(12).bearing(0)
        .tilt(30).build()));
        latitude=centerLatLng.latitude;
        longitude=centerLatLng.longitude;
        mUiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
       // getAddress(latitude,longitude);
       /* changeCamera(
                CameraUpdateFactory.newCameraPosition(new CameraPosition(
                        Constants.ZHONGGUANCUN, 18, 30, 30)));
        aMap.clear();
        aMap.addMarker(new MarkerOptions().position(Constants.ZHONGGUANCUN)
                .icon(BitmapDescriptorFactory
                        .defaultMarker(BitmapDescriptorFactory.HUE_RED)));*/
        addCenterMarker(centerLatLng);
        String m=getAddress(latitude,longitude);
        tvresult.setText(m);

        //tvresult.setText("选中的坐标："+centerLatLng.longitude+","+centerLatLng.latitude);
    }
    private void changeCamera(CameraUpdate update) {
        aMap.moveCamera(update);
    }
    //将经纬度信息转换成地点信息
    public String getAddress(double latitude,double longitude){
        Geocoder geocoder=new Geocoder(this, Locale.getDefault());
        try{
            List addresses=geocoder.getFromLocation(latitude,longitude,1);
           if (addresses.size()>0){

                String data=addresses.get(0).toString();
                tvresult.setText(data);
               int startCity=data.indexOf("locality=")+"locality=".length();


               int endCity=data.indexOf(",",startCity);
                String city=data.substring(startCity,endCity);

                int startPlace=data.indexOf("feature=")+"feature=".length();

                int endPlace=data.indexOf(",",startPlace);

                String place=data.substring(startPlace,endPlace);

                return city+place;
           }
        }
        catch (IOException e){
            e.printStackTrace();

        }
        return "获取失败";
    }

    private  void  addCenterMarker(LatLng latLng){
        if (null ==centerMarker){
            centerMarker=aMap.addMarker(markerOptions);

        }
        centerMarker.setPosition(latLng);
        markerList.add(centerMarker);
    }
    public static final  int UPDATE_MAP=5;
    private  Handler handler=new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_MAP:
                    aMap.setOnMapClickListener(MapActivity1.this);
                    aMap.setLocationSource(MapActivity1.this);//设置定位监听
                    //蓝点初始化
                    myLocationStyle=new MyLocationStyle();//初始化定位蓝点样式类
                    //myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource())
                    myLocationStyle.interval(2000);//设置定位间隔，单位为毫秒
                    aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的style
                    aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示
                    aMap.setMyLocationEnabled(true);//设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位
                    //myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//只在第一次定位移动到地图中心点
                    aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);

                    myLocationStyle.showMyLocation(true);
                    break;
                    default:
                        break;

            }
        }
    };

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
                    my_location=aMapLocation.getAddress();
                    if (isFirst){
                        tvresult.setText(aMapLocation.getAddress());
                        isFirst=false;
                    }
                }
                else {
                    String errText="定位失败,"+aMapLocation.getErrorCode()+":"+aMapLocation.getErrorInfo();
                    Log.d("hhh",errText);
                }
            }
    }
}
