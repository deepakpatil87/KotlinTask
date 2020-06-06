package com.kotlin.taskapp.retrofitapi;

import com.kotlin.taskapp.model.responsemodel.productList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface RequestInterface {


    @GET("/walmartproducts/{pageNumber}/{pageSize}")
    Call<productList> getProductList(@Path("pageNumber") int pageNumber, @Path("pageSize") int pageSize);


}
