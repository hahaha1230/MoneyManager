package com.example.a25467.moneymanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import org.litepal.LitePal;

public class newNote extends AppCompatActivity {
    int mYear,mMonth,mDay;
    Button btn,notessure;
    TextView dateDisplay;
    final int DATE_DIALOG=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        LitePal.getDatabase();
        btn=(Button)findViewById(R.id.coosedate);
        dateDisplay=(TextView)findViewById(R.id.dateDisplay);
        notessure=(Button)findViewById(R.id.notessure);
        final EditText contents=(EditText)findViewById(R.id.contents);
        notessure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notes notes=new notes();
                notes.setDate(dateDisplay.getText().toString());
                notes.setContent(contents.getText().toString());
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG);
            }
        });
        final java.util.Calendar ca= java.util.Calendar.getInstance();
        mYear=ca.get(java.util.Calendar.YEAR);
        mMonth=ca.get(java.util.Calendar.MONTH);
        mDay=ca.get(java.util.Calendar.DAY_OF_MONTH);
    }
    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
            case DATE_DIALOG:
                return new DatePickerDialog(this,mdateListener,mYear,mMonth,mDay);
        }
        return null;
    }
    public void display(){
        dateDisplay.setText(new StringBuffer().append(mMonth+1).append("-").append(mDay).append("-")
                .append(mYear).append(" "));
    }
    private DatePickerDialog.OnDateSetListener mdateListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear=year;
            mMonth=month;
            mDay=dayOfMonth;
            display();
        }
    };
}
