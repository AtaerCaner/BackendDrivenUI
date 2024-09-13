package com.example.dynamicuiexample

import com.example.dynamicuiexample.response.RemoteModel
import com.example.dynamicuiexample.response.ResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("71ce1c6c-8e09-423e-9dd2-ec44f84f6848")
    fun getExampleData(): Call<RemoteModel>
}
