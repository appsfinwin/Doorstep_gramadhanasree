package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo

import com.google.gson.annotations.SerializedName

class Charges(
    @SerializedName("AccNo")
    val accountNumber: String,

    @SerializedName("Charges")
    val charges: String,

    @SerializedName("Charges_id")
    val chargeId: String,

    @SerializedName("Amount")
    val amount: String)
