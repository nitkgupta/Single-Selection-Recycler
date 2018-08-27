package com.jungleworks.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jungleworks.RecylerViewClickListener;
import com.jungleworks.singlerecycler.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.view.View.GONE;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> implements RecylerViewClickListener {


    private List<RetroPhoto> result;
    private Context context;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView img;
    private int last_position=0;
    private int present_position=-1;
    private Parcelable recyclerViewState;



    public MyAdapter(Context context, List<RetroPhoto> res, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager) {
        this.context=context;
        result=res;
        this.recyclerView=recyclerView;
        this.layoutManager=layoutManager;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_for_recycler,parent,false);
        img=(ImageView)view.findViewById(R.id.check);
        ViewHolder mHolder=new ViewHolder(view,this);
//        recyclerView.getLayoutManager().onRestoreInstanceState(recyclerViewState);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState==recyclerView.SCROLL_STATE_IDLE){
                    notifyDataSetChanged(); }
            }

        });

        return mHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Picasso.with(context).load(result.get(position).getThumbnailUrl()).into(holder.iv);

        if( last_position!=-1 && present_position==position){
            img.setVisibility(View.VISIBLE);
            Log.i("TAG",last_position+"--lastPosition--"+position+"--position--"+present_position+"--presentPosition in if");

        }
        else{
            img.setVisibility(GONE);
            Log.i("TAG",last_position+"--lastPosition--"+position+"--position--"+present_position+"--presentPosition in else");

        }
//        recyclerView.getRecycledViewPool().clear();
//        recyclerViewState=recyclerView.getLayoutManager().onSaveInstanceState();
    }

    @Override
    public int getItemCount() {
        return 98;
    }

    @Override
    public void onViewClicked(View view,View parentview) {
        present_position = recyclerView.getChildAdapterPosition(parentview);
        if(present_position!=RecyclerView.NO_POSITION) {

            if(present_position!=-1){

                img=layoutManager.findViewByPosition(last_position).findViewById(R.id.check);
                img.setVisibility(GONE);
                notifyItemChanged(last_position);
                img=layoutManager.findViewByPosition(present_position).findViewById(R.id.check);
                img.setVisibility(View.VISIBLE);
                notifyItemChanged(present_position);
                System.err.println(last_position+" -- myParent");

                last_position=present_position;
                System.err.println(last_position+" -- myParent2");

            }
        }
        else {
            img.setVisibility(View.VISIBLE);
            notifyItemChanged(present_position);
            System.err.println("Called extra else");
        }


    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        ViewHolder(final View parent, final RecylerViewClickListener recylerViewClickListener){
            super(parent);
            iv=(ImageView)parent.findViewById(R.id.imgBack);
            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recylerViewClickListener.onViewClicked(v,parent);

                }
            });
        }

    }

}
