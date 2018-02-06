package com.example.a25467.moneymanager.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.a25467.moneymanager.Class.AccountBookClass;
import com.example.a25467.moneymanager.Activity.NewIncomeActivity;
import com.example.a25467.moneymanager.Activity.NewPayActivity;
import com.example.a25467.moneymanager.Adapter.BookKeppingAdapter;
import com.example.a25467.moneymanager.Datatable.BookKepping_Data_Table;
import com.example.a25467.moneymanager.Datatable.InformationDataTable;
import com.example.a25467.moneymanager.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 25467 on 2018/1/21.
 */

public class BookKeepingFragment extends Fragment implements View.OnClickListener{
    Button a,b,c;//代表收入、支出、总计
    int change=1;//代表三个button状态

    private List<AccountBookClass>accountBookList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationView navigationView;
    public  View headerview;
    private TextView username;

    public BookKeepingFragment(){

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
        navigationView = (NavigationView)getActivity(). findViewById(R.id.nav_view);


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
                    Intent intent=new Intent(getContext(),NewPayActivity.class);
                    startActivity(intent);
                }
                //若在收入界面，点击“+”进入新建收入界面
                else if (change==2){
                    Intent intent=new Intent(getContext(),NewIncomeActivity.class);
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
        BookKeppingAdapter adapter=new BookKeppingAdapter(accountBookList);
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
        BookKeppingAdapter adapter=new BookKeppingAdapter(accountBookList);
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
                    AccountBookClass accountBook=new AccountBookClass("您于"+String.valueOf(aa.getDate())+
                            "花费了"+String.valueOf(aa.getMoney()),aa.getCreate_time());
                    accountBookList.add(accountBook);
                }
                break;
            case 2:
                List<BookKepping_Data_Table>bbs= DataSupport.where("category=?","2")
                        .find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table bb:bbs){
                    AccountBookClass accountBook=new AccountBookClass("您于"+String.valueOf(bb.getDate())+
                            "收入了"+String.valueOf(bb.getMoney()),bb.getCreate_time());
                    accountBookList.add(accountBook);
                }

                break;
            case 3:
                int pay=0,income=0,sum=0;
                Calendar cal;
                String year;
                String month;
                String mytime1;

                cal=Calendar.getInstance();
                cal.setTimeZone(TimeZone.getTimeZone("GTM+8.00"));//设置时区
                year=String.valueOf(cal.get(Calendar.YEAR));
                month=String.valueOf(cal.get(Calendar.MONTH)+1);
                if (Double.valueOf(month)<10){
                    month=0+month;
                }
                mytime1=year+month;
                Log.d("hhh",mytime1);
                //Toast.makeText(getGetContext(), String.valueOf(mytime1),Toast.LENGTH_SHORT).show();

                Long m=Long.valueOf(mytime1)*100;//年+月+00（00代表00天）,用于做本月最小天
                Long n=Long.valueOf(mytime1)*100+32;//年+月+00（32代表32天），用于做本月最大天
                int all_income=0,all_pay=0;
                Double budget_income,budget_pay;
                Log.d("hhh",String.valueOf(m));


                //获取本月的总支出
                List<BookKepping_Data_Table>ees=DataSupport.where("date>? and date< ? and category=?",
                        String.valueOf(m),String.valueOf(n),"1").find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table bookKepping_data_table:ees){
                    all_pay +=bookKepping_data_table.getMoney();
                }
               // 获取本月的总收入
                List<BookKepping_Data_Table>ffs=DataSupport.where("date>? and date< ? and category=?",
                        String.valueOf(m),String.valueOf(n),"2").find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table bookKepping_data_table:ffs){
                    all_income +=bookKepping_data_table.getMoney();
                }
                //获取本月的预算收入与预算支出
                InformationDataTable informationDataTable=DataSupport.findFirst(InformationDataTable.class);
                budget_income=informationDataTable.getBudget_Income();
                budget_pay=informationDataTable.getBudget_Pay();
                //计算本月总收入、本月总支出与预算收入、预算支出的差额
                Double diff_pay=Double.valueOf(all_pay)-budget_pay;
                Double diff_income=Double.valueOf(all_income)-budget_income;

                //获取总支出
                List<BookKepping_Data_Table>ccs= DataSupport.where("category=?","1")
                        .find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table cc:ccs){
                    pay+=cc.getMoney();
                }
                //获取总收入
                List<BookKepping_Data_Table>dds= DataSupport.where("category=?","2")
                        .find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table dd:dds){
                    income+=dd.getMoney();
                }
                //计算总收入
                sum=income-pay;
                //显示出来

                AccountBookClass accountBook1=new AccountBookClass("您本月一共花费了"+String.valueOf(all_pay)+
                        "元，比本月预期花费"
                       +String.valueOf(budget_pay)+"多了"+String.valueOf(diff_pay)+
                                "元。收入了"+String.valueOf(all_income)+"元，比本月预期收入"+String.valueOf(budget_income)
                        +"多了"+String.valueOf(diff_income)+"元。"+"您一共花费了"+String.valueOf(pay)+"元，"
                        +"收入了"+String.valueOf(income)+"元。总收入为"+String.valueOf(sum)+"元",
                        System.currentTimeMillis());
                accountBookList.add(accountBook1);
                break;
            default:
                break;
        }


    }
    //改变字体的颜色
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

        headerview = navigationView.getHeaderView(0);
        username=(TextView)headerview.findViewById(R.id.username);

        List<InformationDataTable> informationDataTables=DataSupport.findAll(InformationDataTable.class);
        for (InformationDataTable informationDataTable:informationDataTables){
            username.setText("欢迎："+informationDataTable.getName());
        }
        initAccountBook();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list1);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        BookKeppingAdapter adapter=new BookKeppingAdapter(accountBookList);
        recyclerView.setAdapter(adapter);
        Toast.makeText(getContext(),"刷新成功！",Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);
    }
}
