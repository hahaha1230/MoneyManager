package com.example.a25467.moneymanager;

import android.support.v4.app.Fragment;
import android.graphics.Color;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.litepal.LitePal;
import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{
    private TextView title,item_notes,item_bookkeeping;
    private ViewPager vp;
    private New_Notes_Fragment newNotes;
    private BookKeeping_Fragment bookKeeping;
    private List<Fragment> mFragementList=new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;


    String []titles=new String[]{"便签","记账"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main2);
        /*BookKepping_Data_Table bookKepping_data_table=new BookKepping_Data_Table();
        bookKepping_data_table.setDate(2017_02_05);
        bookKepping_data_table.setMoney(99);
        bookKepping_data_table.setCategory(1);
        bookKepping_data_table.save();*/
        //Connector.getDatabase();
     // LitePal.getDatabase();



      /* initNotes();
       RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recy_list);
       LinearLayoutManager layoutManager=new LinearLayoutManager(this);
       recyclerView.setLayoutManager(layoutManager);
       Notes_Adapter adapter=new Notes_Adapter(notesList);
       recyclerView.setAdapter(adapter);*/


        initViews();
        mFragmentAdapter=new FragmentAdapter(this.getSupportFragmentManager(),mFragementList);
        vp.setOffscreenPageLimit(2);
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



   /* private void initNotes(){
        int max= DataSupport.count(Notes_Data_table.class);

       String m,n;
        for (int i=1;i<=max;i++){
            Notes_Data_table aa= DataSupport.find(Notes_Data_table.class,i);
            m=aa.getContent();
            n=aa.getDate();

            Notesss notes=new Notesss(m,n);
            notesList.add(notes);

        }
    }*/

    private void initViews(){
        title=(TextView)findViewById(R.id.title);
        item_notes=(TextView)findViewById(R.id.item_notes);
        item_bookkeeping=(TextView)findViewById(R.id.item_Book_Keepping) ;


        item_notes.setOnClickListener(this);
       item_bookkeeping.setOnClickListener(this);

        vp=(ViewPager)findViewById(R.id.mainViewPager);
        bookKeeping=new BookKeeping_Fragment();
        newNotes=new New_Notes_Fragment();



        mFragementList.add(newNotes);
        mFragementList.add(bookKeeping);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.item_notes:
                vp.setCurrentItem(0,true);
                break;
            case R.id.item_Book_Keepping:
                vp.setCurrentItem(1,true);
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
            item_bookkeeping.setTextColor(Color.parseColor("#000000"));
        }
        else  if(position==1){
            item_notes.setTextColor(Color.parseColor("#000000"));
           item_bookkeeping.setTextColor(Color.parseColor("#66CDAA"));

        }
    }
}
