package com.example.dynamicuiexample

import com.example.dynamicuiexample.response.RemoteModel
import com.example.dynamicuiexample.response.ResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("c0b6def0-3724-4edf-bc96-5e18cbc808b6")
    fun getExampleData(): Call<RemoteModel>
}
