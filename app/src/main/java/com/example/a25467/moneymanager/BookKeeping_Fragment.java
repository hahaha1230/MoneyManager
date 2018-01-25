package com.example.a25467.moneymanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25467 on 2018/1/21.
 */

public class BookKeeping_Fragment extends Fragment implements View.OnClickListener{
    Button a,b,c;
    int change=1;
    public int getChange(){
        return change;
    }
    public void setChange(int change){
        this.change=change;
    }
    private List<AccountBook>accountBookList=new ArrayList<>();

    public BookKeeping_Fragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.bookkeepping_layout,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        a=(Button)getActivity().findViewById(R.id.a);
        b=(Button)getActivity().findViewById(R.id.b);
        c=(Button)getActivity().findViewById(R.id.c);
        //ImageButton jia1=(ImageButton)getActivity().findViewById(R.id.jia1);
        changetextcolor();
       a.setOnClickListener(this);
       b.setOnClickListener(this);
       c.setOnClickListener(this);
       //jia1.setOnClickListener(this);






        initAccountBook();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BookKepping_Adapter adapter=new BookKepping_Adapter(accountBookList);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v){

        switch (v.getId()){
            case R.id.a:
                change=1;
                break;
            case R.id.b:
                change=2;
                break;
            case R.id.c:
                change=3;
                break;
            /*case R.id.jia1:
                if (change==1){
                    Intent intent=new Intent(getActivity(),New_Pay.class);
                    startActivity(intent);
                }
                else if (change==2){
                    Intent intent=new Intent(getActivity(),New_Income.class);
                    startActivity(intent);
                }
                else if (change==3){

                }

                break;*/
                default:
                    break;
        }

       /* FloatingActionButton fab=(FloatingActionButton)getActivity().findViewById(R.id.jia1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (change==1){
                    Intent intent=new Intent(getContext(),New_Pay.class);
                    startActivity(intent);
                }
                else if (change==2){
                    Intent intent=new Intent(getContext(),New_Income.class);
                    startActivity(intent);
                }
                else if (change==3){
                    Toast.makeText(getContext(),"没有什么可以新建的！",Toast.LENGTH_SHORT).show();
                }

            }
        });*/

        changetextcolor();


        initAccountBook();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BookKepping_Adapter adapter=new BookKepping_Adapter(accountBookList);
        recyclerView.setAdapter(adapter);
    }


    private void initAccountBook(){
        accountBookList.clear();

        switch (change){
            case 1:
                List<BookKepping_Data_Table> aas= DataSupport.where("category=?","1")
                        .find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table aa:aas){
                   AccountBook accountBook=new AccountBook("您于"+String.valueOf(aa.getDate())+
                           "花费了"+String.valueOf(aa.getMoney()),aa.getCreate_time());
                   accountBookList.add(accountBook);


                }
                break;
            case 2:
                List<BookKepping_Data_Table>bbs= DataSupport.where("category=?","2")
                        .find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table bb:bbs){
                    AccountBook accountBook=new AccountBook("您于"+String.valueOf(bb.getDate())+
                            "收入了"+String.valueOf(bb.getMoney()),bb.getCreate_time());
                    accountBookList.add(accountBook);
                }

                break;
            case 3:
                int pay=0,income=0,sum=0;
                List<BookKepping_Data_Table>ccs= DataSupport.where("category=?","1")
                        .find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table cc:ccs){
                    pay+=cc.getMoney();
                }
                List<BookKepping_Data_Table>dds= DataSupport.where("category=?","2")
                        .find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table dd:dds){
                    income+=dd.getMoney();
                }
                sum=income-pay;
                AccountBook accountBook=new AccountBook("您一共花费了"+String.valueOf(pay)+"元，"
                        +"收入了"+String.valueOf(income)+"元。总收入为"+String.valueOf(sum)+"元",
                        System.currentTimeMillis());
                accountBookList.add(accountBook);
                break;
            default:
                    break;
        }


    }
    private void changetextcolor(){
        if (change==1){
            a.setTextColor(Color.parseColor("#66CDAA"));
            b.setTextColor(Color.parseColor("#000000"));
            c.setTextColor(Color.parseColor("#000000"));
        }
        else if (change==2){
            b.setTextColor(Color.parseColor("#66CDAA"));
            a.setTextColor(Color.parseColor("#000000"));
            c.setTextColor(Color.parseColor("#000000"));
        }
        else  if (change==3){
            c.setTextColor(Color.parseColor("#66CDAA"));
            b.setTextColor(Color.parseColor("#000000"));
            a.setTextColor(Color.parseColor("#000000"));
        }

    }
}
