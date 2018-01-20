package com.example.a25467.moneymanager;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    Button addNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addNotes=(Button)findViewById(R.id.addnotes);
        addNotes.setOnClickListener(this);

    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.addnotes:
                Intent intent1=new Intent(Main2Activity.this,pay.class);
                startActivity(intent1);
                break;
            case R.id.newpay:
                Intent intent2=new Intent(Main2Activity.this,pay.class);
                startActivity(intent2);
                break;
            case R.id.newincome:
                Intent intent3=new Intent(Main2Activity.this,Income.class);
                startActivity(intent3);
                break;
                default:
                    break;
        }
    }
}
