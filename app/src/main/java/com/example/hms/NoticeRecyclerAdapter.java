package com.example.hms;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class NoticeRecyclerAdapter extends RecyclerView.Adapter<NoticeRecyclerAdapter.ViewHolder> {
    private final android.content.Context Context;
    private final LayoutInflater layoutInflater;
    private Cursor mCursor;

    private int mTitlePos;
    private int mContentPos;


    public NoticeRecyclerAdapter(Context mContext, Cursor cursor) {
        this.Context = mContext;
        this.layoutInflater= LayoutInflater.from(mContext);
        this.mCursor=cursor;

        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if(mCursor==null)
            return;

        mContentPos=mCursor.getColumnIndex(HMSDataBaseContract.Notice_info_Entry.COLUMN_content);
        mTitlePos=mCursor.getColumnIndex(HMSDataBaseContract.Notice_info_Entry.COLUMN_title);

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
    public NoticeRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.item_complaint_list,parent,false);
        return new NoticeRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeRecyclerAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String content=mCursor.getString(mContentPos);
        String Title=mCursor.getString(mTitlePos);


        holder.textContent.setText(content);
        holder.textTitle.setText(Title);
        holder.mCurrentPosition=position;
    }

    @Override
    public int getItemCount() {
        return mCursor==null ? 0 : mCursor.getCount();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public final TextView textContent;
        public final TextView textTitle;
        public int mCurrentPosition;

        public ViewHolder(View itemView){
            super(itemView);
            textContent= (TextView) itemView.findViewById(R.id.textView4);
            textTitle= (TextView) itemView.findViewById(R.id.textView3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent =new Intent(Context,NoticeDetailsActivity.class);
                    intent.putExtra("position",mCurrentPosition);
                    Context.startActivity(intent);
                }
            });

        }

    }
}
