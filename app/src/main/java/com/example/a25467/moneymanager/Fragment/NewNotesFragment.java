package com.example.a25467.moneymanager.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a25467.moneymanager.Adapter.NotesAdapter;
import com.example.a25467.moneymanager.Activity.NewNoteActivity;
import com.example.a25467.moneymanager.Class.NotesssClass;
import com.example.a25467.moneymanager.Datatable.InformationDataTable;
import com.example.a25467.moneymanager.Datatable.Notes_Data_table;
import com.example.a25467.moneymanager.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25467 on 2018/1/21.
 */

public class NewNotesFragment extends Fragment {



    private List<NotesssClass> notesList=new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;
    private NavigationView navigationView;
    public  View headerview;
    private TextView username,name,sex;
    private Menu menu;




    public NewNotesFragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.first,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        navigationView = (NavigationView)getActivity(). findViewById(R.id.nav_view);
        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.sex:
                        List<InformationDataTable> informationDataTables=DataSupport.findAll(InformationDataTable.class);
                        for (InformationDataTable informationDataTable:informationDataTables){
                            sex.setText("性别"+informationDataTable.getSex());
                        }

                }
                return false;
            }
        });*/
        //下拉刷新，重新从数据库中获取内容
        swipeRefreshLayout=(SwipeRefreshLayout)getActivity().findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        //点击“+”号，进入新建界面
        FloatingActionButton fab=(FloatingActionButton)getActivity().findViewById(R.id.jia);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent=new Intent(getActivity(),NewNoteActivity.class);
                    startActivity(intent);
            }
        });






        //将内容显示在界面上
        initNotes();
       RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list);
       LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
       recyclerView.setLayoutManager(layoutManager);
       NotesAdapter adapter=new NotesAdapter(notesList);
       recyclerView.setAdapter(adapter);



    }

    private void initNotes(){
        notesList.clear();

        String m,n;
        List <Notes_Data_table>notes_data_tables=DataSupport.findAll(Notes_Data_table.class);
        for (Notes_Data_table nn:notes_data_tables) {
            m=nn.getContent();
            n=String.valueOf(nn.getDate());
            long i=nn.getCreate_time();
            NotesssClass notes=new NotesssClass(m,n,i);
            notesList.add(notes);

        }
       // List<BookKepping_Data_Table>bookKepping_data_tables=DataSupport.findAll(BookKepping_Data_Table.class);
       /* for (Notes_Data_table nn:notes_datatables){
            m=nn.getContent();
            n=nn.getDate();

            NotesssClass notes=new NotesssClass(m,n);
            notesList.add(notes);
        }*/
       /* for (BookKepping_Data_Table bb:bookKepping_data_tables){
            m=bb.getName();
            n=bb.getWhere();
            Log.d("hhh",m);
            Log.d("hhh",n);
            NotesssClass notes=new NotesssClass(m,n);
            notesList.add(notes);
        }

       // String m,n;
       /* for (int i=1;i<=5;i++){
            Notes_Data_table aa= DataSupport.find(Notes_Data_table.class,i);
            m=aa.getContent();
            n=aa.getDate();



        }*/
    }






    //刷新
    private void refresh(){

        headerview = navigationView.getHeaderView(0);
        username=(TextView)headerview.findViewById(R.id.username);



       List<InformationDataTable> informationDataTables=DataSupport.findAll(InformationDataTable.class);
        for (InformationDataTable informationDataTable:informationDataTables){
            username.setText("欢迎："+informationDataTable.getName());
        }



        initNotes();
        RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        NotesAdapter adapter=new NotesAdapter(notesList);
        recyclerView.setAdapter(adapter);
        Toast.makeText(getContext(),"刷新成功！",Toast.LENGTH_SHORT).show();
        swipeRefreshLayout.setRefreshing(false);

    }




}
