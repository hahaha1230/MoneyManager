package com.example.a25467.moneymanager.Adapter;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.a25467.moneymanager.Class.AccountBookClass;
import com.example.a25467.moneymanager.Datatable.BookKepping_Data_Table;
import com.example.a25467.moneymanager.Datatable.InformationDataTable;
import com.example.a25467.moneymanager.R;

import org.litepal.crud.DataSupport;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by 25467 on 2018/1/23.
 */

public class BookKeppingAdapter extends RecyclerView.Adapter<BookKeppingAdapter.ViewHolder>{
    private List<AccountBookClass> mAccountBook;
    static  class ViewHolder extends  RecyclerView.ViewHolder{
        View nview;
        TextView brief_display;
        public ViewHolder(View view){
            super(view);
            nview=view;
            brief_display=(TextView)view.findViewById(R.id.brief);
        }
    }
    public BookKeppingAdapter(List<AccountBookClass>accountBooks){
        mAccountBook=accountBooks;
    }
    @Override
    public  ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view2,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.nview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                AccountBookClass accountBook=mAccountBook.get(position);
                String m="";


                AlertDialog.Builder dialog=new AlertDialog.Builder(v.getContext());
                List<BookKepping_Data_Table>bookKepping_data_tables= DataSupport.where("create_time=?"
                ,String.valueOf(accountBook.getCreate_time())).find(BookKepping_Data_Table.class);
                for (BookKepping_Data_Table bookKepping_data_table:bookKepping_data_tables){
                    //若为支出界面，则用dialog显示支出信息
                    if (bookKepping_data_table.getCategory()==1){
                        m="您于"+String.valueOf(bookKepping_data_table.getDate())+"使用" +
                                bookKepping_data_table.getAccount()+"账户花费了"+
                                bookKepping_data_table.getMoney()+"元用于"+
                                bookKepping_data_table.getSource_or_purpose()+";        备注为:"+
                               bookKepping_data_table.getNotes();
                    }
                    //若为收入界面，则用dialog显示收入信息
                    else  if (bookKepping_data_table.getCategory()==2){
                        m="您于"+String.valueOf(bookKepping_data_table.getDate())+"使用" +
                                bookKepping_data_table.getAccount()+"账户收入了"+
                                bookKepping_data_table.getMoney()+"元来源于"+
                                bookKepping_data_table.getSource_or_purpose()+";        备注为:"+
                                bookKepping_data_table.getNotes();
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
            }
        });
        return  holder;
    }
    @Override
    public  void onBindViewHolder(ViewHolder holder,int position){
        AccountBookClass accountBook=mAccountBook.get(position);
        holder.brief_display.setText(accountBook.getBrief());
    }
    @Override
    public int getItemCount(){
        return  mAccountBook.size();
    }

}
