package com.example.hms;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class ViewComplaintActivity extends AppCompatActivity {

    public RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_complaint);

        recyclerView=(RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<String> complaints=new ArrayList<String>(3);
        complaints.add("First");
        complaints.add("Second");
        complaints.add("Third");
        final ComplaintsRecyclerAdapter complaintsRecyclerAdapter=new ComplaintsRecyclerAdapter(this,complaints);

        recyclerView.setAdapter(complaintsRecyclerAdapter);


    }
//    protected void onStop()
//    {
//        super.onStop();
////        Log.e("HELLO", "HELLO");
////        Intent returnIntent=getIntent();
////        returnIntent.putExtra("THIS_USER_OBJECT", THIS_USER_OBJECT);
////        setResult(RESULT_OK, returnIntent);
////        finish();
//
//    }

//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        switch (item.getItemId()) {
//            case android.R.id.home:
//
//                Intent returnIntent=getIntent();
////                returnIntent.putExtra("THIS_USER_OBJECT", THIS_USER_OBJECT);
//                setResult(RESULT_OK, returnIntent);
//                finish();
//                return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
