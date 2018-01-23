package com.example.a25467.moneymanager;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 25467 on 2018/1/23.
 */

public class BookKepping_Adapter extends RecyclerView.Adapter<BookKepping_Adapter.ViewHolder>{
    private List<AccountBook> mAccountBook;
    static  class ViewHolder extends  RecyclerView.ViewHolder{
        TextView brief_display;
        public ViewHolder(View view){
            super(view);
            brief_display=(TextView)view.findViewById(R.id.brief);
        }
    }
    public BookKepping_Adapter(List<AccountBook>accountBooks){
        mAccountBook=accountBooks;
    }
    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view2,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return  holder;
    }
    @Override
    public  void onBindViewHolder(ViewHolder holder,int position){
        AccountBook accountBook=mAccountBook.get(position);
        holder.brief_display.setText(accountBook.getBrief());
    }
    @Override
    public int getItemCount(){
        return  mAccountBook.size();
    }

}
