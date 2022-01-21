package com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.pojo

import com.google.gson.annotations.SerializedName

data class SearchGroupAccountResponse(
    val `data`: List<GroupAccountData>,
    val status: String,
    val msg: String
)

data class GroupAccountData(
    @SerializedName("Branch Code")
    val BranchCode: String,

    @SerializedName("JLG_GroupCode")
    val JLG_GroupCode: String,


    val JLG_GroupName: String,
    val Ln_GlobalAccNo: String,

    @SerializedName("Scheme")
    val Scheme: String,

    @SerializedName("Scheme Category")
    val SchemeCategory: String,

    @SerializedName("Scheme code")
    val SchemeCode: String,
    val Type: String
)