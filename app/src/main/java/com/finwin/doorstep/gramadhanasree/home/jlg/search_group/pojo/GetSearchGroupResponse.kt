package com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo

import com.google.gson.annotations.SerializedName

data class GetSearchGroupResponse(
    val `data`: List<SearchGroupData>,
    val status: String,

    @SerializedName("msg")
    val message: String
)

data class SearchGroupData(
    @SerializedName("Br_Code")
    val branchCode: String,

    @SerializedName("JLG_CenterCode")
    val centerCode: String,

    @SerializedName("JLG_GroupCode")
    val groupCode: String,

    @SerializedName("JLG_GroupName")
    val groupName: String
)