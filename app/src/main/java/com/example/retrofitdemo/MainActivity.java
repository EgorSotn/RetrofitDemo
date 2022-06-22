package com.example.retrofitdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.retrofitdemo.adapter.CountryAdapter;
import com.example.retrofitdemo.model.CountryInfo;
import com.example.retrofitdemo.model.Result;
import com.example.retrofitdemo.service.CountryInterface;
import com.example.retrofitdemo.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    CountryAdapter countryAdapter;
    RecyclerView recyclerView;
    ArrayList<Result> resultArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCountries();

    }

    private ArrayList<Result> getCountries(){
        CountryInterface countryService = RetrofitInstance.getService();
        Call<CountryInfo> call = countryService.getResult();
        call.enqueue(new Callback<CountryInfo>() {
            @Override
            public void onResponse(Call<CountryInfo> call, Response<CountryInfo> response) {
               CountryInfo countryInfo = response.body();

               if(countryInfo!=null && countryInfo.getRestResponse()!=null){
                   resultArrayList =
                           (ArrayList<Result>) countryInfo.getRestResponse()
                                   .getResult();

                   setRecyclerView();
               }

            }

            @Override
            public void onFailure(Call<CountryInfo> call, Throwable t) {
            }

        });

        return resultArrayList;
    }
    private void setRecyclerView(){
       recyclerView = findViewById(R.id.recycler_view);
       countryAdapter = new CountryAdapter(resultArrayList);
       RecyclerView.LayoutManager linearLayout = new LinearLayoutManager(this);
       recyclerView.setLayoutManager(linearLayout);
       recyclerView.setAdapter(countryAdapter);
    }
}