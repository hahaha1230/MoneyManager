package com.example.a25467.moneymanager.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25467.moneymanager.Datatable.BookKepping_Data_Table;
import com.example.a25467.moneymanager.R;

public class NewIncomeActivity extends Activity implements View.OnClickListener {
    private Button choose_Account;
    private Button sure_income;
    private Button quit_income;
    private Button chooseLocate;
    private TextView sure_Account;
    private TextView dateDisplay;
    private TextView locateDisplay;
    private EditText num;
    private EditText category;
    private EditText notes1;
    private Button choose_date;
    final int DATE_DIALOG = 1;
    int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_income);

        initView();

    }

    /**
     * 初始化界面
     */
    public void initView(){
        choose_Account = (Button) findViewById(R.id.choose_Account);
        choose_date = (Button) findViewById(R.id.choosedate1);
        sure_income = (Button) findViewById(R.id.sure_income);
        quit_income = (Button) findViewById(R.id.quit_income);
        sure_Account = (TextView) findViewById(R.id.sure_Account);
        dateDisplay = (TextView) findViewById(R.id.dateDisplay1);
        chooseLocate = (Button) findViewById(R.id.chooseLocate);
        locateDisplay = (TextView) findViewById(R.id.locateDisplay);
        num = (EditText) findViewById(R.id.num);
        category = (EditText) findViewById(R.id.category);
        notes1 = (EditText) findViewById(R.id.notes1);
        choose_Account.setOnClickListener(this);
        choose_date.setOnClickListener(this);
        sure_income.setOnClickListener(this);
        quit_income.setOnClickListener(this);
        chooseLocate.setOnClickListener(this);


        final java.util.Calendar ca = java.util.Calendar.getInstance();
        mYear = ca.get(java.util.Calendar.YEAR);
        mMonth = ca.get(java.util.Calendar.MONTH);
        mDay = ca.get(java.util.Calendar.DAY_OF_MONTH);
    }

    /**
     * 设置界面点击事件，包括选择时间、地点、账户、确定、取消等
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.choosedate1:
                showDialog(DATE_DIALOG);
                break;
            case R.id.choose_Account:
                choose_Account.showContextMenu();
                break;
            case R.id.chooseLocate:
                Intent intent = new Intent(NewIncomeActivity.this, MapActivity.class);
                startActivityForResult(intent, 4);
                break;
            case R.id.sure_income:
                String m = "";
                try {

                    //截取字符串中的数字
                    String str = dateDisplay.getText().toString();
                    str.trim();
                    String str2 = "";
                    if (str != null && !"".equals(str)) {
                        for (int i = 0; i < str.length(); i++) {
                            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                                str2 += str.charAt(i);
                            }
                        }
                    }
                    //保存数据
                    BookKepping_Data_Table bookKepping_data_table = new BookKepping_Data_Table();
                    bookKepping_data_table.setNotes(notes1.getText().toString());
                    bookKepping_data_table.setLocate(locateDisplay.getText().toString());
                    bookKepping_data_table.setSource_or_purpose(category.getText().toString());
                    bookKepping_data_table.setAccount(sure_Account.getText().toString());
                    bookKepping_data_table.setMoney(Double.parseDouble(num.getText().toString()));
                    bookKepping_data_table.setCategory(2);
                    bookKepping_data_table.setDate(Long.parseLong(str2));
                    bookKepping_data_table.setCreate_time(System.currentTimeMillis());
                    bookKepping_data_table.save();
                    m = "您的新的收入信息已经保存!";
                    finish();

                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    m = "您输入的信息有误，请重新输入";
                } finally {
                    Toast.makeText(NewIncomeActivity.this, m, Toast.LENGTH_SHORT).show();
                }
                break;

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
                menu.add(2, 2, 2, "信用卡");
                menu.add(3, 3, 3, "支付宝");
                menu.add(4, 4, 4, "微信钱包");
                menu.add(5, 5, 5, "QQ红包");

            }
        });

    }

    /**
     * 获取地图界面选取的地点信息
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case 4:
                if (resultCode == RESULT_OK) {
                    locateDisplay.setText(data.getExtras().getString("result"));
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getGroupId()) {
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
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG:
                return new DatePickerDialog(this, mdateListener, mYear, mMonth, mDay);
        }
        return null;
    }

    //将时间格式规范化并显示日期
    public void display() {
        if (mMonth < 10 && mDay < 10) {

            dateDisplay.setText(new StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).
                    append("月").append("0").append(mDay).append("日"));
        } else if (mMonth >= 10 && mDay < 10) {
            dateDisplay.setText(new StringBuffer().append(mYear).append("年").append(mMonth + 1).
                    append("月").append("0").append(mDay).append("日"));
        } else if (mMonth < 10 && mDay >= 10) {
            dateDisplay.setText(new StringBuffer().append(mYear).append("年").append("0").append(mMonth + 1).
                    append("月").append(mDay).append("日"));
        } else {
            dateDisplay.setText(new StringBuffer().append(mYear).append("年").append(mMonth + 1).
                    append("月").append(mDay).append("日"));
        }
    }

    private DatePickerDialog.OnDateSetListener mdateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mYear = year;
            mMonth = month;
            mDay = dayOfMonth;
            display();
        }
    };
}

