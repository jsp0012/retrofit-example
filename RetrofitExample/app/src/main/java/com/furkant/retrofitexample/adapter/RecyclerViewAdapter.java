package com.furkant.retrofitexample.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.furkant.retrofitexample.R;
import com.furkant.retrofitexample.model.CryptoModel;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {

    private HashMap<String, Object> cryptoList;

    private String[] colors = {"#a3ff00","#ff00aa","#b4a7d6","#a4c2f4","#8ee5ee","#cd950c","#f5f5f5","#f47932"};

    public RecyclerViewAdapter(HashMap<String, Object> cryptoList) {
        this.cryptoList = cryptoList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RowHolder holder, int position) {

        ArrayList<CryptoModel> resultModelList = new ArrayList<CryptoModel>();
        for (Map.Entry<String, Object> entry : cryptoList.entrySet())
            if(entry.getValue() instanceof LinkedTreeMap) { // Since the first element is of type String i.e. "Update_Date"
                CryptoModel doviz = new Gson().fromJson(new Gson().toJson(((LinkedTreeMap<String, Object>) entry.getValue())), CryptoModel.class);
                CryptoModel tempModel = new CryptoModel();
                tempModel.type=entry.getKey();
                tempModel.buying=doviz.buying;
                tempModel.selling=doviz.selling;
                tempModel.change=doviz.change;
                resultModelList.add(tempModel);
            }
            holder.bind(resultModelList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName;
        TextView textPrice;

        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void bind(CryptoModel cryptoModel,String[] colors, Integer position) {
            itemView.setBackgroundColor(Color.parseColor("#a4c2f4"));
            textName = itemView.findViewById(R.id.text_name);
            textPrice = itemView.findViewById(R.id.text_price);
            textName.setText(cryptoModel.type);
            textPrice.setText(cryptoModel.selling);

        }


    }
}
