package com.example.dynamicuiexample

import com.example.dynamicuiexample.response.RemoteModel
import com.example.dynamicuiexample.response.ResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("b6578064-c165-4725-9934-99437a53ad22")
    fun getExampleData(): Call<RemoteModel>
}
