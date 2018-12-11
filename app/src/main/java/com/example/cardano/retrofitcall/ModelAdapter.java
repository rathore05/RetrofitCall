package com.example.cardano.retrofitcall;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ModelAdapter extends RecyclerView.Adapter<ModelAdapter.MyViewHolder> {

    private ArrayList<Model> mList;
    Context mContext;

    ModelAdapterOnClickHandler mClickHandler;

    public ModelAdapter(ArrayList<Model> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    private interface ModelAdapterOnClickHandler {
        void onClick(int rate);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View mView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_list, viewGroup, false);
        return new MyViewHolder(mView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {

        myViewHolder.mId.setText(mList.get(i).getId());
        myViewHolder.mName.setText(mList.get(i).getName());
        myViewHolder.mRatings.setText(String.valueOf(mList.get(i).getRating()));

        myViewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, mList.get(myViewHolder.getAdapterPosition()).getId() + "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView mId, mName, mRatings;
        private View view;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            mId = itemView.findViewById(R.id.tv_id);
            mName = itemView.findViewById(R.id.tv_name);
            mRatings = itemView.findViewById(R.id.tv_rating);

        }

        @Override
        public void onClick(View v) {

        }
    }
}
