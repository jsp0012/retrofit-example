package com.furkant.retrofitexample.model;

import com.google.gson.annotations.SerializedName;

public class CryptoModel {

    @SerializedName("Type")
    public String type;

    @SerializedName("Buying")
    public String buying;

    @SerializedName("Selling")
    public String selling;

    @SerializedName("Change")
    public String change;

/*
"Buying": "11.0348",
        "Type": "Currency",
        "Selling": "11.0501",
        "Change": "-0.57"
 */
}
