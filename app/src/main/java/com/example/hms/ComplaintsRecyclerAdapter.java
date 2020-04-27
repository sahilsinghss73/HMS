package com.example.hms;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ComplaintsRecyclerAdapter extends RecyclerView.Adapter<ComplaintsRecyclerAdapter.ViewHolder>{

    private final Context Context;
    private final LayoutInflater layoutInflater;
    private final ArrayList<String> complaints;
    public ComplaintsRecyclerAdapter(Context mContext, ArrayList<String> complaints) {
        this.Context = mContext;
        this.layoutInflater= LayoutInflater.from(mContext);
        this.complaints = complaints;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.item_complaint_list,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        char arr[]=complaints[position].toCharArray();
        holder.textCourse.setText(complaints.get(position));
        holder.textTitle.setText(complaints.get(position));
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView textCourse;
        public final TextView textTitle;

        public ViewHolder(View itemView){
            super(itemView);
            textCourse= (TextView) itemView.findViewById(R.id.textView4);
            textTitle= (TextView) itemView.findViewById(R.id.textView3);


        }

    }
}
