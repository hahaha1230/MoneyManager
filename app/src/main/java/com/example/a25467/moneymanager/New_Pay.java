package com.example.a25467.moneymanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class New_Pay extends Activity implements View.OnClickListener{
    Button choose_Account, choose_date, sure_pay, quit_pay,notes_sure,notes_quit;
    TextView sure_Account;
    TextView dateDisplay;
    EditText num, purpose, notes1;
    int mYear,mMonth,mDay;
    final int DATE_DIALOG=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        choose_date = (Button)findViewById(R.id.choose_date);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay);
        choose_Account = (Button)findViewById(R.id.choose_Account);
        sure_Account = (TextView)findViewById(R.id.sure_Account);
        choose_date = (Button) findViewById(R.id.choosedate1);
        num = (EditText) findViewById(R.id.num);
        purpose = (EditText)findViewById(R.id.purpose);
        notes1 = (EditText) findViewById(R.id.notes1);
        sure_pay = (Button) findViewById(R.id.sure_pay);
        quit_pay = (Button) findViewById(R.id.quit_pay);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay1);
        choose_Account.setOnClickListener(this);
        choose_date.setOnClickListener(this);
        sure_pay.setOnClickListener(this);
        quit_pay.setOnClickListener(this);

        final  java.util.Calendar ca=java.util.Calendar.getInstance();
        mYear=ca.get(Calendar.YEAR);
        mMonth=ca.get(Calendar.MONTH);
        mDay=ca.get(Calendar.DAY_OF_MONTH);



    }

    @Override
    public void onClick(View v) {
        String information =null;
        switch (v.getId()) {
            case R.id.choosedate1:
                showDialog(DATE_DIALOG);
                break;

            case R.id.choose_Account:
                choose_Account.showContextMenu();
                break;
            case R.id.sure_pay:
                try {
                    BookKepping_Data_Table bookKepping_data_table= new BookKepping_Data_Table();
                    bookKepping_data_table = new BookKepping_Data_Table();
                    bookKepping_data_table.setCategory(1);
                    bookKepping_data_table.setMoney(Double.parseDouble(num.getText().toString()));
                    bookKepping_data_table.setAccount(choose_Account.getText().toString());
                    bookKepping_data_table.setDate(Long.parseLong(dateDisplay.getText().toString()));
                    bookKepping_data_table.setSource_or_purpose(purpose.getText().toString());
                    bookKepping_data_table.setNotes(notes1.getText().toString());
                    bookKepping_data_table.save();
                    information="您的新的支出信息已保存";
                } catch (Exception e) {
                    e.printStackTrace();
                    information="您输入的信息有误，请重新输入！";
                } finally {
                    Toast.makeText(New_Pay.this,information,Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.quit_income:
                sure_Account.setText(null);
                purpose.setText(null);
                dateDisplay.setText(null);
                notes1.setText(null);
                num.setText(null);
                break;
            default:
                break;
        }
        choose_Account.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, 0, "现金");
                menu.add(1, 1, 1, "储蓄卡");
                menu.add(2,2,2,"信用卡");
                menu.add(3,3,3,"支付宝");
                menu.add(4,4,4,"微信钱包");
                menu.add(5,5,5,"蚂蚁花呗");
                menu.add(6,6,6,"QQ红包");
            }

        });
    }
    @Override
    public boolean onContextItemSelected(MenuItem item){
        switch (item.getGroupId()){
            case 0:
                sure_Account.setText("现金");
                break;
            case 1:
                sure_Account.setText("储蓄卡");
                break;
            case 2:
                sure_Account.setText("信用卡");
                break;
            case 3:
                sure_Account.setText("支付宝");
                break;
            case 4:
                sure_Account.setText("微信钱包");
                break;
            case 5:
                sure_Account.setText("蚂蚁花呗");
                break;
            case 6:
                sure_Account.setText("QQ红包");
                break;
            default:
                break;
        }
        return  true;
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
        /*dateDisplay.setText(new StringBuffer().append(mMonth+1).append("-").append(mDay).append("-")
                .append(mYear).append(" "));*/
        dateDisplay.setText(new StringBuffer().append(mYear).append("年").append(mMonth+1).append("月").append(mDay)
        .append("日"));
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
