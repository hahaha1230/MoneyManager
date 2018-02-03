package com.example.a25467.moneymanager.Activity;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.a25467.moneymanager.Datatable.InformationDataTable;
import com.example.a25467.moneymanager.R;

import org.litepal.crud.DataSupport;

import java.util.List;

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    Button sure_Modify,quit_Modify;
    EditText budget_Pay,budget_Income,name;
    RadioGroup sex;
    String m;//存储性别

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sure_Modify=(Button)findViewById(R.id.sure_Modify);
        quit_Modify=(Button)findViewById(R.id.quit_Modify);
        budget_Income=(EditText)findViewById(R.id.budget_Income);
        budget_Pay=(EditText)findViewById(R.id.budget_Pay);
        name=(EditText)findViewById(R.id.name);
        sex=(RadioGroup)findViewById(R.id.sex);
        sure_Modify.setOnClickListener(this);
        quit_Modify.setOnClickListener(this);



        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.men:
                        m="男";
                        break;
                    case R.id.women:
                        m="女";
                        break;
                        default:
                            break;


                }
            }
        });
        //在edittext中用sethint方法给用者提示原本的信息
        List<InformationDataTable>informationDataTables= DataSupport.findAll(InformationDataTable.class);
        for (InformationDataTable informationDataTable:informationDataTables){
            name.setHint(informationDataTable.getName());
            budget_Pay.setHint(informationDataTable.getBudget_Pay().toString());
            budget_Income.setHint(informationDataTable.getBudget_Income().toString());
        }
    }
    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.sure_Modify:
                String information="";
                try{

                    //更新数据
                    InformationDataTable informationDataTable=new InformationDataTable();
                    informationDataTable.setName(name.getText().toString());
                    informationDataTable.setSex(m);
                    informationDataTable.setBudget_Income(Double.valueOf(budget_Income.getText().toString()));
                    informationDataTable.setBudget_Pay(Double.valueOf(budget_Pay.getText().toString()));
                    informationDataTable.updateAll();
                    information="信息修改成功！";
                    finish();


                }
                catch (Exception e) {
                    e.printStackTrace();
                    information="您输入的信息有误，请重新输入！";
                } finally {
                    Toast.makeText(SettingActivity.this,information,Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.quit_income:
                break;
                default:
                    break;


        }

    }
}
