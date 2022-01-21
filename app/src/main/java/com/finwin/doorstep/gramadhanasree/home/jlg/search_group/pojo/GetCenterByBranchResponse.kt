package com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo

import com.google.gson.annotations.SerializedName

data class GetCenterByBranchResponse(
    val `data`: List<CenterData>,
    val status: String
)

data class CenterData(
    @SerializedName("BranchCode")
    val branchCode: String,

    @SerializedName("BranchName")
    val branchName: String,

    @SerializedName("Center Code")
    val centerCode: String,

    @SerializedName("Center Name")
    val centerName: String,

    @SerializedName("Maker Id")
    val makerId: String,

    @SerializedName("Making Time")
    val makingTime: String
)