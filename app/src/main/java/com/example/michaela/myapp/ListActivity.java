package com.example.michaela.myapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.michaela.myapp.database.WorkoutContract;
import com.example.michaela.myapp.database.WorkoutDBHelper;

public class ListActivity extends AppCompatActivity {

    private SQLiteDatabase mDataBase;
    private WorkoutAdapter mAdapter;
    private EditText mEditTextName;
    private TextView mTextViewAmount;
    private int mAmount = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //add back btn
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        WorkoutDBHelper dbHelper = new WorkoutDBHelper(this);
        mDataBase = dbHelper.getWritableDatabase();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new WorkoutAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                removeItem((long) viewHolder.itemView.getTag());
            }
        }).attachToRecyclerView(recyclerView);

        mEditTextName = findViewById(R.id.edittext_name);
        //mTextViewAmount = findViewById(R.id.textview_amount);
        mTextViewAmount = findViewById(R.id.textview_amount);

        Button buttonIncrease = findViewById(R.id.button_plus);
        final Button buttonDecrease = findViewById(R.id.button_minus);
        Button buttonAdd = findViewById(R.id.button_add);

        buttonIncrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increase();
            }
        });

        buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrease();
            }
        });

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home){
            //ends this activity
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void increase(){
        mAmount++; // +1 to this variable
        mTextViewAmount.setText(String.valueOf(mAmount));
    }

    private void  decrease(){
        if(mAmount > 0){
            mAmount--;
            mTextViewAmount.setText(String.valueOf(mAmount));
        }
    }

    private void addItem(){
        if(mEditTextName.getText().toString().trim().length() == 0 || mAmount == 0) {
            return;
        }
        String name = mEditTextName.getText().toString();
        ContentValues cv = new ContentValues();
        cv.put(WorkoutContract.WorkoutEntry.COLUMN_NAME, name);
        cv.put(WorkoutContract.WorkoutEntry.COLUMN_AMOUNT, mAmount);
        //adding value for the id & timestamp column is being done automatically

        mDataBase.insert(WorkoutContract.WorkoutEntry.TABLE_NAME, null, cv);
        mAdapter.swapCursor(getAllItems());
        mEditTextName.getText().clear(); //make the field empty for the next entry
    }

    private void removeItem(long id){
        mDataBase.delete(WorkoutContract.WorkoutEntry.TABLE_NAME,
                WorkoutContract.WorkoutEntry._ID + "=" + id, null);
        mAdapter.swapCursor(getAllItems());
    }

    private Cursor getAllItems(){
        return mDataBase.query(
                WorkoutContract.WorkoutEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                WorkoutContract.WorkoutEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}
