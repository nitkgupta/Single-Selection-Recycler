package com.jungleworks.singlerecycler;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.jungleworks.adapter.GetImage;
import com.jungleworks.adapter.MyAdapter;
import com.jungleworks.adapter.RetroPhoto;
import com.jungleworks.adapter.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity  {
    MyAdapter mAdapter;
    RecyclerView rvList;
    ProgressDialog progressDialog;
    int selected=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvList=(RecyclerView)findViewById(R.id.rvFirst);
        progressDialog= new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();


        GetImage image= RetrofitClient.getInstance().create(GetImage.class);
        Call<List<RetroPhoto>> call=image.getAllPhotos();
        call.enqueue(new Callback<List<RetroPhoto>>() {
            @Override
            public void onResponse(Call<List<RetroPhoto>> call, Response<List<RetroPhoto>> response) {
                progressDialog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<RetroPhoto>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Some error occured!! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    protected void onStart(){
        super.onStart();

    }
    public void generateDataList(List<RetroPhoto> photoList){
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,7);
        mAdapter = new MyAdapter(this,photoList,rvList,layoutManager);
        rvList.setLayoutManager(layoutManager);
        rvList.setAdapter(mAdapter);

    }

}
