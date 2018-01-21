package com.example.a25467.moneymanager;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private TextView title,item_notes,item_pay,item_income,item_datamanager;
    private ViewPager vp;
    private  New_Income newIncome;
    private New_Notes newNotes;
    private  New_Pay newPay;
    private DataManager dataManager;
    private List<Fragment> mFragementList=new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
   // Button addNotes;


    String []titles=new String[]{"便签","新建支出","新建收入","数据管理"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);
        initViews();


        mFragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),mFragementList);
        vp.setOffscreenPageLimit(4);
        vp.setAdapter(mFragmentAdapter);
        vp.setCurrentItem(0);
        item_notes.setTextColor(Color.parseColor("#66CDAA"));


        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                title.setText(titles[position]);
                changeTextColor(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }

    private void initViews(){
        title=(TextView)findViewById(R.id.title);
        item_pay=(TextView)findViewById(R.id.item_pay);
        item_datamanager=(TextView)findViewById(R.id.item_datamanager);
        item_income=(TextView)findViewById(R.id.item_income);
        item_notes=(TextView)findViewById(R.id.item_notes);



        item_notes.setOnClickListener(this);
        item_income.setOnClickListener(this);
        item_datamanager.setOnClickListener(this);
        item_pay.setOnClickListener(this);



        vp=(ViewPager)findViewById(R.id.mainViewPager);
        newIncome=new New_Income();
        dataManager=new DataManager();
        newNotes=new New_Notes();
        newPay=new New_Pay();



        mFragementList.add(newNotes);
        mFragementList.add(newPay);
        mFragementList.add(newIncome);
        mFragementList.add(dataManager);

    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.item_notes:
                vp.setCurrentItem(0,true);
                break;
            case R.id.item_pay:
                vp.setCurrentItem(1,true);
                break;
            case R.id.item_income:
                vp.setCurrentItem(2,true);
                break;
            case R.id.item_datamanager:
                vp.setCurrentItem(3,true);
                break;
            default:
                    break;
        }
    }

    public class FragmentAdapter extends FragmentPagerAdapter{

        List<Fragment>fragmentList=new ArrayList<Fragment>();

        public  FragmentAdapter(FragmentManager fm,List<Fragment>fragmentList){
            super(fm);
            this.fragmentList=fragmentList;
        }
        @Override
        public Fragment getItem(int position){
            return fragmentList.get(position);
        }
        @Override
        public  int getCount(){
            return fragmentList.size();
        }
    }
    private  void changeTextColor(int position){
        if(position==0){
            item_notes.setTextColor(Color.parseColor("#66CDAA"));
            item_pay.setTextColor(Color.parseColor("#000000"));
            item_datamanager.setTextColor(Color.parseColor("#000000"));
            item_income.setTextColor(Color.parseColor("#000000"));
        }
        else  if(position==1){
            item_notes.setTextColor(Color.parseColor("#000000"));
            item_pay.setTextColor(Color.parseColor("#66CDAA"));
            item_income.setTextColor(Color.parseColor("#000000"));
            item_datamanager.setTextColor(Color.parseColor("#000000"));
        }
        else if (position==2){
            item_datamanager.setTextColor(Color.parseColor("#000000"));
            item_notes.setTextColor(Color.parseColor("#000000"));
           item_income.setTextColor(Color.parseColor("#66CDAA"));
            item_pay.setTextColor(Color.parseColor("#000000"));
        }
        else if (position==3){
           item_income.setTextColor(Color.parseColor("#000000"));
            item_notes.setTextColor(Color.parseColor("#000000"));
           item_pay.setTextColor(Color.parseColor("#000000"));
           item_datamanager.setTextColor(Color.parseColor("#66CDAA"));
        }
    }
}
