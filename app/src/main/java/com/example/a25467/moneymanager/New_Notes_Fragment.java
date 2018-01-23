package com.example.a25467.moneymanager;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 25467 on 2018/1/21.
 */

public class New_Notes_Fragment extends Fragment {

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

        ImageButton jia=(ImageButton)getActivity().findViewById(R.id.jia);
        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),NewNote_Activity.class);
                startActivity(intent);
            }
        });

        /*BookKepping_Data_Table bookKepping_data_table=new BookKepping_Data_Table();
        bookKepping_data_table.setDate(" 2017年");
        bookKepping_data_table.setName("huahua");
        bookKepping_data_table.setWhere("xizhaisunzhuangchun");
        bookKepping_data_table.save();*/



        initNotes();
       RecyclerView recyclerView=(RecyclerView)getActivity().findViewById(R.id.recy_list);
       LinearLayoutManager layoutManager=new LinearLayoutManager(getActivity());
       recyclerView.setLayoutManager(layoutManager);
       Notes_Adapter adapter=new Notes_Adapter(notesList);
       recyclerView.setAdapter(adapter);



    }

    private void initNotes(){
        String m,n;
        List <Notes_Data_table>notes_data_tables=DataSupport.findAll(Notes_Data_table.class);
        for (Notes_Data_table nn:notes_data_tables) {
            m=nn.getContent();
            n=nn.getDate();
            Log.d("hhh",m);
            Log.d("hhh",n);
            Notesss notes=new Notesss(m,n);
            notesList.add(notes);

        }
       // List<BookKepping_Data_Table>bookKepping_data_tables=DataSupport.findAll(BookKepping_Data_Table.class);
       /* for (Notes_Data_table nn:notes_datatables){
            m=nn.getContent();
            n=nn.getDate();

            Notesss notes=new Notesss(m,n);
            notesList.add(notes);
        }*/
       /* for (BookKepping_Data_Table bb:bookKepping_data_tables){
            m=bb.getName();
            n=bb.getWhere();
            Log.d("hhh",m);
            Log.d("hhh",n);
            Notesss notes=new Notesss(m,n);
            notesList.add(notes);
        }

       // String m,n;
       /* for (int i=1;i<=5;i++){
            Notes_Data_table aa= DataSupport.find(Notes_Data_table.class,i);
            m=aa.getContent();
            n=aa.getDate();



        }*/
    }




}
