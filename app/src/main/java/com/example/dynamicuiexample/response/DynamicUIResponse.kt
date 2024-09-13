package com.example.dynamicuiexample.response

import com.google.gson.annotations.SerializedName

class RemoteModel {
    @SerializedName("data")
    val data: ArrayList<ResponseData> = ArrayList()
}

data class ResponseData(
    @SerializedName("children") val children: ArrayList<ResponseData> = ArrayList(),
    @SerializedName("top_bar") val topBar: ArrayList<ResponseData> = ArrayList(),
    @SerializedName("type") var type: ComponentType = ComponentType.UNKNOWN,
    @SerializedName("value") var value: String = "",
    @SerializedName("size") val size: Int = 0,
    @SerializedName("id") val id: String = "",
    @SerializedName("selectionId") val selectionId: String = ""
)

enum class ComponentType {
    TEXT,
    ROW,
    COLUMN,
    RADIO_BUTTON,
    RADIO_GROUP,
    UNKNOWN
}