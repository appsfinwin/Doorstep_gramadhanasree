package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.pojo

import com.google.gson.annotations.SerializedName

data class getjLgCenterResponse(
    val `data`: List<CenterData>,
    val status: String
)

data class CenterData(
    val BranchCode: String,
    val BranchName: String,

    @SerializedName("Center Code")
    val CenterCode: String,

    @SerializedName("Center Name")
    val CenterName: String,

    @SerializedName("Maker Id")
    val MakerId: String,

    @SerializedName("Making Time")
    val MakingTime: String
)