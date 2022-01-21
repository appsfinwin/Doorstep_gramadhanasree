package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.pojo

import com.google.gson.annotations.SerializedName

data class JlgPendingListResponse(
    val `data`: List<PendingData>,
    val status: String,
    val msg: String
)

data class PendingData(
    @SerializedName("Acc No")
    val accountNumber: String,

    @SerializedName("Branch")
    val branch: String,

    @SerializedName("Center Name")
    val centerName: String,

    @SerializedName("Group Name")
    val groupName: String,

    @SerializedName("Loan Type")
    val loanType : String,

    @SerializedName("Period Type")
    val periodType: String,

    @SerializedName("Purpose")
    val purpose: String,

    @SerializedName("SubPurpose")
    val subPurpose: String
)