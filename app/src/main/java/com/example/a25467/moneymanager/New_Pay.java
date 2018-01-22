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

import org.litepal.LitePal;

/**
 * Created by 25467 on 2018/1/21.
 */

public class New_Pay extends Fragment implements View.OnClickListener {
    Button choose_Account, choose_date, sure_pay, quit_pay;
    TextView sure_Account;
    TextView dateDisplay;
    EditText num, purpose, where, notes1;

    public New_Pay() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_pay, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        choose_date = (Button) getActivity().findViewById(R.id.choosedate);
        dateDisplay = (TextView) getActivity().findViewById(R.id.dateDisplay);
        LitePal.getDatabase();
        choose_Account = (Button) getActivity().findViewById(R.id.choose_Account);
        sure_Account = (TextView) getActivity().findViewById(R.id.sure_Account);
        choose_date = (Button) getActivity().findViewById(R.id.choosedate1);
        num = (EditText) getActivity().findViewById(R.id.num);
        purpose = (EditText) getActivity().findViewById(R.id.purpose);
        where = (EditText) getActivity().findViewById(R.id.where);
        notes1 = (EditText) getActivity().findViewById(R.id.notes1);
        sure_pay = (Button) getActivity().findViewById(R.id.sure_pay);
        quit_pay = (Button) getActivity().findViewById(R.id.quit_pay);
        dateDisplay = (TextView) getActivity().findViewById(R.id.dateDisplay1);
        choose_Account.setOnClickListener(this);
        choose_date.setOnClickListener(this);
        sure_pay.setOnClickListener(this);
        quit_pay.setOnClickListener(this);

        choose_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choose_Account:
                choose_Account.showContextMenu();
                break;
            case R.id.sure_pay:
                int i = Integer.valueOf(num.getText().toString()).intValue();
                Pay_Datatable payClass = new Pay_Datatable();
                payClass.setAccount(choose_Account.getText().toString());
                payClass.setDate(dateDisplay.getText().toString());
                payClass.setMoney(i);
                payClass.setPurpose(purpose.getText().toString());
                payClass.setWhere(where.getText().toString());
                payClass.setNotes(notes1.getText().toString());
                payClass.save();
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
}
