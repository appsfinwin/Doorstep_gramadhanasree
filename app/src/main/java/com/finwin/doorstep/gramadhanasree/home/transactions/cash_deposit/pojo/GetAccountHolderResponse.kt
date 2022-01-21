package com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo

data class GetAccountHolderResponse(
    val account: Account
)

data class Account(
    val `data`: Data,
    val error: String
)

data class Data(
    val ACC_STATUS: String,
    val ACNO: String,
    val CURRENT_BALANCE: String,
    val CUST_ID: String,
    val MOBILE: String,
    val NAME: String
)