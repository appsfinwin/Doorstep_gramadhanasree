package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo

import com.google.gson.annotations.SerializedName

data class LoanSubmitResponse(
    val `data`: Data
)

data class Data(
        val AccNo: String,
        val Date: String,
        val Time: String,
        val dat: List<Dat>,
        val msg: String
)

data class Dat(
    @SerializedName("Current Receipt")
    val currentReceipt: String,

    @SerializedName("Particular")
    val particular: String
)