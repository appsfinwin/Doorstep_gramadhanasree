package com.finwin.doorstep.gramadhanasree.home.jlg.search_member.pojo

import com.google.gson.annotations.SerializedName

data class GetSearchMemberResponse(
    @SerializedName("customer_list")
    val customerList: CustomerList
)

data class CustomerList(
    val `data`: List<MemberData>,
    val error:String
)

data class MemberData(
    @SerializedName("ACC_NO")
    val accountNumber: String,

    @SerializedName("CUST_ID")
    val customerId: String,

    @SerializedName("CUST_NAME")
    var customerName: String,

    @SerializedName("MOBILE")
    val mobile: String,

    @SerializedName("REFNO")
    val refNo: String
)