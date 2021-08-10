package com.dmcbproject.dailycollection.responsedata;


import android.text.Editable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseReportdatum {

    @SerializedName("b_code")
    @Expose
    private String bCode;
    @SerializedName("mi_collection")
    @Expose
    private String miCollection;
    @SerializedName("deposit_collection")
    @Expose
    private String depositCollection;
    @SerializedName("post_date")
    @Expose
    private String postDate;

    public ResponseReportdatum(String bCode, String miCollection, String depositCollection, String postDate) {
        this.bCode = bCode;
        this.miCollection = miCollection;
        this.depositCollection = depositCollection;
        this.postDate = postDate;
    }



    public String getbCode() {
        return bCode;
    }

    public void setbCode(String bCode) {
        this.bCode = bCode;
    }

    public String getMiCollection() {
        return miCollection;
    }

    public void setMiCollection(String miCollection) {
        this.miCollection = miCollection;
    }

    public String getDepositCollection() {
        return depositCollection;
    }

    public void setDepositCollection(String depositCollection) {
        this.depositCollection = depositCollection;
    }

    public String getPostDate() {
        return postDate;
    }

    public void setPostDate(String postDate) {
        this.postDate = postDate;
    }

}