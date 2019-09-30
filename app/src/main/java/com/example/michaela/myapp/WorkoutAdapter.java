package com.example.michaela.myapp;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.michaela.myapp.database.WorkoutContract;

public class WorkoutAdapter extends RecyclerView.Adapter<WorkoutAdapter.WorkoutViewHolder> {

    private Context mContext;
    private Cursor mCursor;

    public WorkoutAdapter(Context context, Cursor cursor) {
        mContext = context;
        mCursor = cursor;
    }

    public class WorkoutViewHolder extends RecyclerView.ViewHolder{

        public TextView nameText;
        public TextView countText;

        public WorkoutViewHolder(@NonNull View itemView) {
            super(itemView);

            nameText = itemView.findViewById(R.id.textview_name_item);
            countText = itemView.findViewById(R.id.textview_amount_item);
        }
    }

    @NonNull
    @Override
    public WorkoutViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.workout_item, parent, false);
        return new WorkoutViewHolder(view);
    }

    @Override
    public void onBindViewHolder(WorkoutViewHolder holder, int position) {
        if(!mCursor.moveToPosition(position)){
            return;
        }
        String name = mCursor.getString(mCursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_NAME));
        int amount = mCursor.getInt(mCursor.getColumnIndex(WorkoutContract.WorkoutEntry.COLUMN_AMOUNT));
        long id = mCursor.getLong(mCursor.getColumnIndex(WorkoutContract.WorkoutEntry._ID));

        holder.nameText.setText(name);
        holder.countText.setText(String.valueOf(amount));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }
    //
    public void swapCursor(Cursor newCursor) {
        if(mCursor != null){
            mCursor.close();
        }
        mCursor = newCursor;
        if(newCursor != null){
            notifyDataSetChanged();
        }
    }
}
