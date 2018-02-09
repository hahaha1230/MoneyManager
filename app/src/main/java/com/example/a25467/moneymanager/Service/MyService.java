package com.example.a25467.moneymanager.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.a25467.moneymanager.Class.GetContext;

public class MyService extends Service {
    public AMapLocationClient mLocationClient=null;
    public AMapLocationClientOption mLocationOption=null;

    Double latitude=0.0;
    Double longitude=0.0;

    public MyService() {
    }



        public AMapLocationListener mLocationListener=new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation!=null){
                    if (aMapLocation.getErrorCode()==0){
                        latitude=aMapLocation.getLatitude();
                        longitude=aMapLocation.getLongitude();
                    }
                }
                else {
                    Log.d("hhh",aMapLocation.getErrorCode()+",errinfo:"+aMapLocation.getErrorInfo());
                }

            }
        };
        public void  d(){
            mLocationClient=new AMapLocationClient(GetContext.getContext());
            mLocationClient.setLocationListener(mLocationListener);
            mLocationOption=new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(1000);
            mLocationOption.setNeedAddress(true);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }



    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");

    }
    @Override
    public void onCreate(){
        super.onCreate();
    }
    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        return super.onStartCommand(intent,flags,startId);
    }
    @Override
    public void onDestroy(){
        super.onDestroy();
        mLocationClient.stopLocation();
    }
}
