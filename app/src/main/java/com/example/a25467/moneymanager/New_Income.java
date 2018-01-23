package com.example.a25467.moneymanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by 25467 on 2018/1/21.
 */

public class New_Income extends Fragment implements View.OnClickListener{
    Button choose_Account,btn,sure_income,quit_income;
    TextView sure_Account;
    TextView dateDisplay;
    EditText num,category,where,notes1;
    public New_Income(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.activity_income,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        choose_Account=(Button)getActivity().findViewById(R.id.choose_Account);
        btn=(Button)getActivity().findViewById(R.id.choosedate1);
        sure_income=(Button)getActivity().findViewById(R.id.sure_income);
        quit_income=(Button)getActivity().findViewById(R.id.quit_income);
        sure_Account=(TextView)getActivity().findViewById(R.id.sure_Account);
        dateDisplay=(TextView)getActivity().findViewById(R.id.dateDisplay1);
        num=(EditText)getActivity().findViewById(R.id.num);
        category=(EditText)getActivity().findViewById(R.id.category);
        notes1=(EditText)getActivity().findViewById(R.id.notes1);
        choose_Account.setOnClickListener(this);
        btn.setOnClickListener(this);
        sure_income.setOnClickListener(this);
        quit_income.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_Account:
                choose_Account.showContextMenu();
                break;
            case R.id.sure_income:
                int i = Integer.valueOf(num.getText().toString()).intValue();
                Income_class incomeClass = new Income_class();
                incomeClass.setAccount(sure_Account.getText().toString());
                incomeClass.setCategory(category.getText().toString());
                incomeClass.setDate(dateDisplay.getText().toString());
                incomeClass.setNotes(notes1.getText().toString());
                incomeClass.setMoney(i);
                incomeClass.save();
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
}
