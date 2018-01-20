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

import org.litepal.LitePal;

public class pay extends AppCompatActivity implements View.OnClickListener {
    Button choose_Account,btn,sure_pay,quit_pay;
    TextView sure_Account;
    TextView dateDisplay;
    EditText num,purpose,where,notes1;
    final int DATE_DIALOG=1;
    int mYear,mMonth,mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        LitePal.getDatabase();
        choose_Account=(Button)findViewById(R.id.choose_Account);
        sure_Account=(TextView)findViewById(R.id.sure_Account);
        btn=(Button)findViewById(R.id.choosedate1);
        num=(EditText)findViewById(R.id.num) ;
        purpose=(EditText)findViewById(R.id.purpose);
        where=(EditText)findViewById(R.id.where);
        notes1=(EditText)findViewById(R.id.notes1);
        sure_pay=(Button)findViewById(R.id.sure_pay);
        quit_pay=(Button)findViewById(R.id.quit_pay);
        dateDisplay=(TextView)findViewById(R.id.dateDisplay1);
        choose_Account.setOnClickListener(this);
        btn.setOnClickListener(this);
        sure_pay.setOnClickListener(this);
        quit_pay.setOnClickListener(this);


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
            case R.id.sure_pay:
                int i=Integer.valueOf(num.getText().toString()).intValue();
                pay_class payClass=new pay_class();
                payClass.setAccount(choose_Account.getText().toString());
                payClass.setDate(dateDisplay.getText().toString());
                payClass.setMoney(i);
                payClass.setPurpose(purpose.getText().toString());
                payClass.setWhere(where.getText().toString());
                payClass.setNotes(notes1.getText().toString());
                payClass.save();
                Toast.makeText(pay.this,"您的信息已经保存",Toast.LENGTH_SHORT).show();
            case R.id.quit_income:
                choose_Account.setText(null);
                purpose.setText(null);
                dateDisplay.setText(null);
                where.setText(null);
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
