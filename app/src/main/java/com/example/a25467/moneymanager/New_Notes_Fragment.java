package com.example.a25467.moneymanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25467 on 2018/1/21.
 */

public class New_Notes_Fragment extends Fragment {
    Button choose_date;
    TextView dateDisplay;
    private List<Notesss> notesList=new ArrayList<>();




    public New_Notes_Fragment(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.first,container,false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
            initNotes();
       RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list);
       LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
       recyclerView.setLayoutManager(layoutManager);
       Notes_Adapter adapter=new Notes_Adapter(notesList);
       recyclerView.setAdapter(adapter);

    }
    private void initNotes(){
       for (int i=0;i<20;i++) {
           Notesss notes = new Notesss("xixixi", "hfdhfhdh");
           notesList.add(notes);
       }

       /* int max= DataSupport.count(notes_datatable.class);
        Log.d("tag",String.valueOf(max));


        String m,n;
        if (max !=0) {
            for (int i = 1; i <= max; i++) {
                notes_datatable aa = DataSupport.find(notes_datatable.class, i);
                m = aa.getContent();
                n = aa.getDate();
                if (m == "" && n == "") {
                    Log.d("tag", "lalalala");
                } else {
                    Log.d("tag", "xixixixi");
                }


                Notesss notes = new Notesss("xixixi", "hfdhfhdh");
                notesList.add(notes);

            }
        }
        else {
            Log.d("tag","first");
        }*/
    }




}
