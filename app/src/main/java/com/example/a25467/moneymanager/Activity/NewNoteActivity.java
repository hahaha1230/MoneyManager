package com.example.a25467.moneymanager.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25467.moneymanager.Datatable.Notes_Data_table;
import com.example.a25467.moneymanager.R;

import java.util.Calendar;

public class NewNoteActivity extends Activity implements View.OnClickListener{
    int mYear,mMonth,mDay;
    Button choose_date,notes_sure,notes_quit;
    TextView dateDisplay;
    EditText contents;
    final int DATE_DIALOG=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);
        choose_date=(Button)findViewById(R.id.choose_date);
        notes_sure=(Button)findViewById(R.id.notes_sure);
        notes_quit=(Button)findViewById(R.id.notes_quit);
        dateDisplay=(TextView)findViewById(R.id.dateDisplay);
        contents=(EditText)findViewById(R.id.contents);
        choose_date.setOnClickListener(this);
        notes_quit.setOnClickListener(this);
        notes_sure.setOnClickListener(this);



        final  java.util.Calendar ca=java.util.Calendar.getInstance();
        mYear=ca.get(Calendar.YEAR);
        mMonth=ca.get(Calendar.MONTH);
        mDay=ca.get(Calendar.DAY_OF_MONTH);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.choose_date:
                showDialog(DATE_DIALOG);
                break;
            case R.id.notes_sure:

                //提取出来时间数字
                String str= null;
                String m="";
                try {
                    str = dateDisplay.getText().toString();
                    str.trim();
                    String str2="";
                    if (str !=null&&!"".equals(str)){
                        for (int i=0;i<str.length();i++){
                            if (str.charAt(i)>=48 &&str.charAt(i)<=57){
                                str2+=str.charAt(i);
                            }
                        }
                    }
                    //保存数据
                    Notes_Data_table notes_data_table=new Notes_Data_table();
                    notes_data_table.setContent(contents.getText().toString());
                    notes_data_table.setDate(Long.parseLong(str2));
                    notes_data_table.setCreate_time(System.currentTimeMillis());
                    notes_data_table.save();
                    m="保存成功！";
                    finish();
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    m="您输入的信息有误，请重新输入！";
                } finally {
                    Toast.makeText(NewNoteActivity.this,m,Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.notes_quit:
                contents.setText("");
                dateDisplay.setText("");
                break;
                default:
                    break;

        }
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
        if (mMonth<10&&mDay<10){

            dateDisplay.setText(new StringBuffer().append(mYear).append("年").append("0").append(mMonth+1).
                    append("月").append("0").append(mDay).append("日"));
        }
        else  if (mMonth>=10&&mDay<10){
            dateDisplay.setText(new StringBuffer().append(mYear).append("年").append(mMonth+1).
                    append("月").append("0").append(mDay).append("日"));
        }

        else if (mMonth<10&&mDay>=10) {
            dateDisplay.setText(new StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).
                    append("月").append(mDay).append("日"));
        }
        else {
            dateDisplay.setText(new StringBuffer().append(mYear).append("年").append(mMonth + 1).
                    append("月").append(mDay).append("日"));
        }
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
