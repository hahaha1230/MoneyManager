package com.example.a25467.moneymanager;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Income extends AppCompatActivity implements View.OnClickListener{
    Button choose_Account,btn,sure_income,quit_income;
    TextView sure_Account;
    TextView dateDisplay;
    EditText num,category,where,notes1;
    final int DATE_DIALOG=1;
    int mYear,mMonth,mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income);
        choose_Account=(Button)findViewById(R.id.choose_Account);
        btn=(Button)findViewById(R.id.choosedate1);
        sure_income=(Button)findViewById(R.id.sure_income);
        quit_income=(Button)findViewById(R.id.quit_income);
        sure_Account=(TextView)findViewById(R.id.sure_Account);
        dateDisplay=(TextView)findViewById(R.id.dateDisplay1);
        num=(EditText)findViewById(R.id.num);
        category=(EditText)findViewById(R.id.category);
        where=(EditText)findViewById(R.id.where);
        notes1=(EditText)findViewById(R.id.notes1);
        choose_Account.setOnClickListener(this);
        btn.setOnClickListener(this);
        sure_income.setOnClickListener(this);
        quit_income.setOnClickListener(this);


        final java.util.Calendar ca= java.util.Calendar.getInstance();
        mYear=ca.get(java.util.Calendar.YEAR);
        mMonth=ca.get(java.util.Calendar.MONTH);
        mDay=ca.get(java.util.Calendar.DAY_OF_MONTH);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.choose_Account:
                choose_Account.showContextMenu();
                break;
            case R.id.choosedate1:
                showDialog(DATE_DIALOG);
                break;
            case R.id.sure_income:
                int i=Integer.valueOf(num.getText().toString()).intValue();
                Income_class incomeClass=new Income_class();
                incomeClass.setAccount(sure_Account.getText().toString());
                incomeClass.setCategory(category.getText().toString());
                incomeClass.setDate(dateDisplay.getText().toString());
                incomeClass.setNotes(notes1.getText().toString());
                incomeClass.setMoney(i);
                incomeClass.save();
                Toast.makeText(Income.this,"您的信息已经保存",Toast.LENGTH_SHORT).show();
            case R.id.quit_income:
                sure_Account.setText(null);
                category.setText(null);
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
                menu.add(5,5,5,"QQ红包");

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
                sure_Account.setText("QQ红包");
                break;
                default:
                    break;
        }
        return true;
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
