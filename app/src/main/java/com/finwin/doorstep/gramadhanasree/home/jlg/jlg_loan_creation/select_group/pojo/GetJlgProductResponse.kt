package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo

import com.google.gson.annotations.SerializedName

data class GetJlgProductResponse (
    val `data`: List<ProductData>,
    val status: String,

    @SerializedName("msg")
val message:String
)

data class ProductData(
    @SerializedName("ProductName")
    val productName: String,

    @SerializedName("ProductId")
    val productId: String,

    @SerializedName("Amount")
    val amount: String,


)