package com.example.a25467.moneymanager.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


import com.example.a25467.moneymanager.Class.AccountBook;
import com.example.a25467.moneymanager.Activity.NewIncome;
import com.example.a25467.moneymanager.Activity.NewPay;
import com.example.a25467.moneymanager.Adapter.BookKepping;
import com.example.a25467.moneymanager.Datatable.BookKepping_Data_Table;
import com.example.a25467.moneymanager.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25467 on 2018/1/21.
 */

public class BookKeeping extends Fragment implements View.OnClickListener{
    Button a,b,c;//代表收入、支出、总计
    int change=1;//代表三个button状态

    private List<AccountBook>accountBookList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;

    public BookKeeping(){

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


        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh1);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();//调用刷新方法
            }
        });

        FloatingActionButton fab=(FloatingActionButton)getActivity().findViewById(R.id.jia1);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //若在支出界面，点击“+”进入新建支出界面
                if (change==1){
                    Intent intent=new Intent(getContext(),NewPay.class);
                    startActivity(intent);
                }
                //若在收入界面，点击“+”进入新建收入界面
                else if (change==2){
                    Intent intent=new Intent(getContext(),NewIncome.class);
                    startActivity(intent);
                }
                //若在总计界面，点击“+”弹出提示

                else if (change==3){
                    Toast.makeText(getContext(),"没有什么可以新建的！",Toast.LENGTH_SHORT).show();


                }




            }
        });






        //将内容显示在recyclerview当中
        initAccountBook();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BookKepping adapter=new BookKepping(accountBookList);
        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onClick(View v){
        //判断哪个button被点击以确定change值

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

            default:
                break;
        }


        //改变字体颜色
        changetextcolor();


        //将内容显示在recyclerview当中
        initAccountBook();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BookKepping adapter=new BookKepping(accountBookList);
        recyclerView.setAdapter(adapter);
    }


    private void initAccountBook(){
        //将accountbooklist内容清空
        accountBookList.clear();


        //根据change值判断应该在recyclerview中显示什么内容
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
    //下拉刷新，重新从数据库中获取信息
    private void refresh(){
        initAccountBook();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BookKepping adapter=new BookKepping(accountBookList);
        recyclerView.setAdapter(adapter);
        Toast.makeText(getContext(),"刷新成功！",Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }
}
