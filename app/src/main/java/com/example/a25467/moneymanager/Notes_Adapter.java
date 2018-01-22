package com.example.a25467.moneymanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 25467 on 2018/1/22.
 */

public class Notes_Adapter extends RecyclerView.Adapter <Notes_Adapter.ViewHolder>{
    private List <Notesss> mNotes;
    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView notes_display;
        TextView date_display;
        public ViewHolder(View view){
            super(view);
            notes_display=(TextView)view.findViewById(R.id.content_display);
            date_display=(TextView)view.findViewById(R.id.date_display);
        }
    }
    public Notes_Adapter(List <Notesss> notes){
        mNotes=notes;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view,parent,false);
        ViewHolder holder=new ViewHolder(view);
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
