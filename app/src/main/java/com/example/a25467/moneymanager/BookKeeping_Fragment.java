package com.example.a25467.moneymanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 25467 on 2018/1/21.
 */

public class BookKeeping_Fragment extends Fragment {

    public BookKeeping_Fragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.bookkeepping_layout,container,false);
    }
}
