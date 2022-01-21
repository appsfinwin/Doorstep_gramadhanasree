package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo

import com.google.gson.annotations.SerializedName

data class SplitTransactionResponse(
    val Id: String,
    //val Status: String,
    val dt: List<Dt>,
    val msg: String,
    val status: String
)

data class Dt(
    @SerializedName("Account Number")
    val accountNumber: String,

    @SerializedName("Charge Amount")
    val chargeAmount: String,

    @SerializedName("Effective Date")
    val effectiveDate: String,

    @SerializedName("Remittance Amount")
    val remittanceAmount: String,

    @SerializedName("Scheme")
    val scheme: String,

    @SerializedName("SubTransactionType")
    val subTransactionType: String,

    @SerializedName("Tran Type")
    val tranType: String
)