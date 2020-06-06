package com.kotlin.taskapp.model.responsemodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class productList(
    @SerializedName("pageNumber")
    var pageNumber: Int = 0,
    @SerializedName("pageSize")
    var pageSize: Int = 0,
    @SerializedName("products")
    var products: List<Product> = listOf(),
    @SerializedName("statusCode")
    var statusCode: Int = 0,
    @SerializedName("totalProducts")
    var totalProducts: Int = 0

) : Parcelable {}
