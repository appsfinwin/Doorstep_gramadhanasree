package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo

import com.google.gson.annotations.SerializedName

data class GetGroupSelectResponse(
    val `data`: List<GroupData>,
    val status: String,

    @SerializedName("msg")
    val message:String
)

data class GroupData(
    @SerializedName("Co_app_Slno")
    val slNumber: String,

    @SerializedName("Cust_Id")
    val customerId: String,

    @SerializedName("Cust_Name")
    val customerName: String
)