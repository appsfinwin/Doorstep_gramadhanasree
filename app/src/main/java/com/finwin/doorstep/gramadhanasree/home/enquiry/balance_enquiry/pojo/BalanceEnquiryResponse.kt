package com.finwin.doorstep.gramadhanasree.home.enquiry.balance_enquiry.pojo

data class BalanceEnquiryResponse(
    val balance: Balance
)

data class Balance(
    val `data`: BalanceData
)

data class BalanceData(
    val ACC_NO: String,
    val CURRENT_BALANCE: String,
    val DATE: String,
    val MOBILE: String,
    val NAME: String
)