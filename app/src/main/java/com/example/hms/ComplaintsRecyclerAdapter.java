package com.example.hms;

import android.content.Context;
import android.database.Cursor;
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
    private Cursor mCursor;

    private int mTitlePos;
    private int mContentPos;

    public ComplaintsRecyclerAdapter(Context mContext, Cursor cursor) {
        this.Context = mContext;
        this.layoutInflater= LayoutInflater.from(mContext);
        this.mCursor=cursor;

        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if(mCursor==null)
            return;

        mContentPos=mCursor.getColumnIndex(HMSDataBaseContract.Complaint_info_Entry.COLUMN_content);
        mTitlePos=mCursor.getColumnIndex(HMSDataBaseContract.Complaint_info_Entry.COLUMN_title);

    }

    public void changeCursor(Cursor cursor)
    {
        if(mCursor!=null)
            mCursor.close();
        mCursor=cursor;
        populateColumnPositions();
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.item_complaint_list,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String content=mCursor.getString(mContentPos);
        String Title=mCursor.getString(mTitlePos);

        holder.textContent.setText(content);
        holder.textTitle.setText(Title);
    }

    @Override
    public int getItemCount() {
        return mCursor==null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView textContent;
        public final TextView textTitle;

        public ViewHolder(View itemView){
            super(itemView);
            textContent= (TextView) itemView.findViewById(R.id.textView4);
            textTitle= (TextView) itemView.findViewById(R.id.textView3);


        }

    }
}
