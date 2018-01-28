package com.example.a25467.moneymanager.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a25467.moneymanager.Datatable.Notes_Data_table;
import com.example.a25467.moneymanager.Class.Notesss;
import com.example.a25467.moneymanager.R;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by 25467 on 2018/1/22.
 */

public class Notes extends RecyclerView.Adapter <Notes.ViewHolder>{
    private List <Notesss> mNotes;
    static class ViewHolder extends RecyclerView.ViewHolder{
        View notesView;
        TextView notes_display;
        TextView date_display;
        public ViewHolder(View view){
            super(view);
            notesView=view;
            notes_display=(TextView)view.findViewById(R.id.content_display);
            date_display=(TextView)view.findViewById(R.id.date_display);
        }
    }
    public Notes(List <Notesss> notes){
        mNotes=notes;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.notesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position=holder.getAdapterPosition();
                Notesss notesss=mNotes.get(position);
                String m="";


                AlertDialog.Builder dialog=new AlertDialog.Builder(v.getContext());
                List<Notes_Data_table>notes_data_tables= DataSupport.where("create_time=?",
                       String.valueOf(notesss.getCreate_time())).find(Notes_Data_table.class);
                for (Notes_Data_table notes_data_table:notes_data_tables){
                    m="时间为:"+String.valueOf(notes_data_table.getDate())+"        "+"内容为："+notes_data_table.getContent();
                }

                dialog.setTitle("详细信息如下：");
                dialog.setMessage(m);
                dialog.setCancelable(false);
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        Notesss notes=mNotes.get(position);
        holder.notes_display.setText(notes.getNotes());
        holder.date_display.setText(notes.getDate());
    }
    @Override
    public int getItemCount(){
        return mNotes.size();
    }

}
