package com.example.a25467.moneymanager.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.example.a25467.moneymanager.R;

public class SettingActivity extends AppCompatActivity {
    Button sure_Modify,quit_Modify;
    EditText budget_Pay,budget_Income,name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sure_Modify=(Button)findViewById(R.id.sure_Modify);
        quit_Modify=(Button)findViewById(R.id.quit_Modify);
        budget_Income=(EditText)findViewById(R.id.budget_Income);
        budget_Pay=(EditText)findViewById(R.id.budget_Pay);
        name=(EditText)findViewById(R.id.name);
    }
}
