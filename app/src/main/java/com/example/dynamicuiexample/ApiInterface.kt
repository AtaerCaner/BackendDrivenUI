package com.example.dynamicuiexample

import com.example.dynamicuiexample.response.RemoteModel
import com.example.dynamicuiexample.response.ResponseData
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("d9d79c54-1549-4741-b707-31249c1f5632")
    fun getExampleData(): Call<RemoteModel>
}
