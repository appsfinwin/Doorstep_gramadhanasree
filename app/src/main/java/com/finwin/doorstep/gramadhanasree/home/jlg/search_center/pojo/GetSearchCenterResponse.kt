package com.finwin.doorstep.gramadhanasree.home.jlg.search_center.pojo

import com.google.gson.annotations.SerializedName

data class GetSearchCenterResponse(
    val `data`: List<CenterSearchData>,
    val status: String,
    val msg: String
)

data class CenterSearchData(
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