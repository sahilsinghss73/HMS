package com.example.hms;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class StudentsRecyclerAdapter extends RecyclerView.Adapter<StudentsRecyclerAdapter.ViewHolder> {
    private final android.content.Context Context;
    private final LayoutInflater layoutInflater;
    private Cursor mCursor;

    private int mNamePos;
    private int mRollPos;

    public StudentsRecyclerAdapter(Context mContext, Cursor cursor) {
        this.Context = mContext;
        this.layoutInflater= LayoutInflater.from(mContext);
        this.mCursor=cursor;

        populateColumnPositions();
    }

    private void populateColumnPositions() {
        if(mCursor==null)
            return;

        mNamePos=mCursor.getColumnIndex(HMSDataBaseContract.Student_info_Entry.COLUMN_name);
        mRollPos=mCursor.getColumnIndex(HMSDataBaseContract.Student_info_Entry.COLUMN_roll_no);

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
    public StudentsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=layoutInflater.inflate(R.layout.item_complaint_list,parent,false);
        return new StudentsRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsRecyclerAdapter.ViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String roll=mCursor.getString(mRollPos);
        String name=mCursor.getString(mNamePos);

        holder.textContent.setText(roll);
        holder.textTitle.setText(name);
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
