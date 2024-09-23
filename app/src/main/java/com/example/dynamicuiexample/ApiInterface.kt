package com.example.dynamicuiexample

import com.example.dynamicuiexample.response.RemoteModel
import com.example.dynamicuiexample.response.ResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("1e4e937b-938c-4d52-a7b7-d2e2c44b99bc")
    fun getExampleData(): Call<RemoteModel>
}
