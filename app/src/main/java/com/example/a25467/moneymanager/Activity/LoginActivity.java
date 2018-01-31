package com.example.a25467.moneymanager.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.a25467.moneymanager.R;

public class LoginActivity extends AppCompatActivity {
//public static final EditText pwd;
public int date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button login=findViewById(R.id.login);
        final EditText account=findViewById(R.id.account);
        final EditText pwd=findViewById(R.id.pwd);
         //date=Integer.parseInt(pwd.getText().toString());

       /* BookKepping_Data_Table bookKepping_data_table=new BookKepping_Data_Table();
        bookKepping_data_table.setDate(" 2017年");
        bookKepping_data_table.setName("huahua");
        bookKepping_data_table.setWhere("xizhaisunzhuangchun");
        bookKepping_data_table.save();

       String m,n;
        List<BookKepping_Data_Table> bookKepping_data_tables= DataSupport.findAll(BookKepping_Data_Table.class);
            for (BookKepping_Data_Table bb:bookKepping_data_tables) {
                m = bb.getName();
                n = bb.getWhere();
                Log.d("hhh", m);
                Log.d("hhh", n);
            }*/
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("sa".equals(account.getText().toString()) &&"sa".equals(pwd.getText().toString())){
                    Intent intent=new Intent(LoginActivity.this,MainInterfaceActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(LoginActivity.this,"您输入的账号或密码有误，请重新输入"
                            ,Toast.LENGTH_SHORT).show();
                    pwd.setText(null);
                    account.setText(null);
                }

            }
        });
    }
}
