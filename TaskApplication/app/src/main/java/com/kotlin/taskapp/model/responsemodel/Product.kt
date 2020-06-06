package com.kotlin.taskapp.model.responsemodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("inStock")
    var inStock: Boolean = false,
    @SerializedName("longDescription")
    var longDescription: String = "",
    @SerializedName("price")
    var price: String = "",
    @SerializedName("productId")
    var productId: String = "",
    @SerializedName("productImage")
    var productImage: String = "",
    @SerializedName("productName")
    var productName: String = "",
    @SerializedName("reviewCount")
    var reviewCount: Int = 0,
    @SerializedName("reviewRating")
    var reviewRating: Float = 0.0f,
    @SerializedName("shortDescription")
    var shortDescription: String = ""


) : Parcelable {}
