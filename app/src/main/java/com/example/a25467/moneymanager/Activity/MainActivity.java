package com.example.a25467.moneymanager.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.a25467.moneymanager.Class.GetContext;
import com.example.a25467.moneymanager.R;

public class MainActivity extends AppCompatActivity {
    public AMapLocationClient mLocationClient=null;
    public AMapLocationClientOption mLocationOption=null;
    public  Double latitude=0.0;
    public  Double longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        d();
        Log.d("hhh",latitude.toString()+"2222");


    }



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
        protected  void onDestroy(){
        super.onDestroy();
       // mLocationClient.stopLocation();
        }


    public AMapLocationListener mLocationListener=new AMapLocationListener() {
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation!=null){
           //if (aMapLocation.getErrorCode()==0){
            Log.d("hhh",String.valueOf(aMapLocation.getErrorCode()));
                Log.d("hhh","zhengque");
                latitude=aMapLocation.getLatitude();
                Log.d("hhh","精度为111111"+String.valueOf(latitude));

                longitude=aMapLocation.getLongitude();
           // }
        }
        else {
            Log.d("hhh",aMapLocation.getErrorCode()+",errinfo:"+aMapLocation.getErrorInfo());
        }

    }
};
}
