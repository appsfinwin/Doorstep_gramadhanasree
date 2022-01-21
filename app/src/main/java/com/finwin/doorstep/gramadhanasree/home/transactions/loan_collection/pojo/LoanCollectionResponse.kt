package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo

import com.google.gson.annotations.SerializedName

data class LoanCollectionResponse(
    val receipt: Receipt
)

data class Receipt(
    val `data`: Data
)

data class Data(
        @SerializedName("AccNo")
    val accountNumber: String,

        @SerializedName("Date")
    val date: String,

        @SerializedName("Time")
    val time: String,

        @SerializedName("dat")
    val dat: List<Dat>,

        @SerializedName("msg")
    val msg: String,

        @SerializedName("status")
    val status: String
)

data class Dat(
    @SerializedName("Current Receipt")
    val currentReceipt: String,

    @SerializedName("Particular")
    val particular: String
)