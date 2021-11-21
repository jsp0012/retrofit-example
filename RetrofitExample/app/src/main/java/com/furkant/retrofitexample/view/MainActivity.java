package com.furkant.retrofitexample.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.CrossProcessCursor;
import android.os.Bundle;

import com.furkant.retrofitexample.R;
import com.furkant.retrofitexample.adapter.RecyclerViewAdapter;
import com.furkant.retrofitexample.model.CryptoModel;
import com.furkant.retrofitexample.service.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    CryptoModel cryptoModels;
    private String BASE_URL = "https://finans.truncgil.com/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;

    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //https://api.nomics.com/v1/prices?key=2187154b76945f2373394aa34f7dc98a
        //https://raw.githubusercontent.com/atilsamancioglu/K21-JSONDataSet/master/crypto.json

        recyclerView = findViewById(R.id.recyclerView);

        //Retrofit & JSON

        Gson gson = new GsonBuilder().setLenient().create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        loadData();

    }

    private void loadData(){

        final CryptoAPI cryptoAPI = retrofit.create(CryptoAPI.class);
/*
        compositeDisposable = new CompositeDisposable();

        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));

        */


        Call<HashMap<String, Object>> call = cryptoAPI.getData();
        call.enqueue(new Callback<HashMap<String, Object>>() {

            @Override
            public void onResponse(Call<HashMap<String, Object>> call, Response<HashMap<String, Object>> response) {
                HashMap<String, Object> responseModel = response.body();

                recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                recyclerViewAdapter = new RecyclerViewAdapter(responseModel);
                recyclerView.setAdapter(recyclerViewAdapter);

                // Iterate all the currencies
                for (Map.Entry<String, Object> entry : responseModel.entrySet())
                    if(entry.getValue() instanceof LinkedTreeMap) { // Since the first element is of type String i.e. "Update_Date"
                        CryptoModel doviz = new Gson().fromJson(new Gson().toJson(((LinkedTreeMap<String, Object>) entry.getValue())), CryptoModel.class);
                        System.out.println("Currency = " + entry.getKey() +
                                ", Buying = " + doviz.buying);
                    }
            }

            @Override
            public void onFailure(Call<HashMap<String, Object>> call, Throwable t) {

            }


        });



    }


}