package com.dmcbproject.dailycollection.apicall;

import android.content.SharedPreferences;

import com.dmcbproject.dailycollection.responsedata.ResponseBranchInfo;
import com.dmcbproject.dailycollection.responsedata.ResponseReportdatum;
import com.dmcbproject.dailycollection.responsedata.Root;

import java.lang.reflect.Parameter;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import static android.content.Context.MODE_PRIVATE;

public interface Api {



    @FormUrlEncoded
    @POST("report")
    Call<ResponseBody> postUser(
            @Field("b_code") String b_code,
            @Field("mi_collection") String mi_collection,
            @Field("deposit_collection") String deposit_collection,
            @Field("post_date") String post_date
    );

    @GET("report")
    Call<List<ResponseReportdatum>> getReportdata();

    @GET("branch")
    Call<List<ResponseBranchInfo>> getBranchdata();


    @GET("jhgj")
    Call<List<ResponseReportdatum>> getSearchdata();


    @FormUrlEncoded
    @POST("login")
    Call<ResponseBody> loginUser(
            @Field("branch_code") String branch_code,
            @Field("email") String email,
            @Field("password") String password
    );


    @POST("login")
    Call<ResponseBody> logout();

}
