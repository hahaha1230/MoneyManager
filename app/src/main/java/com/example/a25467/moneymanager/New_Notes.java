package com.example.a25467.moneymanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

/**
 * Created by 25467 on 2018/1/21.
 */

public class New_Notes extends DialogFragment {
    int mYear,mMonth,mDay;
    Button choose_date;
    TextView dateDisplay;
    final int DATE_DIALOG=1;


    public New_Notes(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_new_note,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
           choose_date=(Button)getActivity().findViewById(R.id.choosedate);
           dateDisplay=(TextView)getActivity().findViewById(R.id.dateDisplay);

        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
    /*public Dialog OnCreateDialog(Bundle savedInstanceState){
        final Calendar calendar=Calendar.getInstance();
        int year=calendar.get(Calendar.YEAR);
        int month=calendar.get(Calendar.MONTH);
        int day=calendar.get(Calendar.DAY_OF_MONTH);
    }*/




}
