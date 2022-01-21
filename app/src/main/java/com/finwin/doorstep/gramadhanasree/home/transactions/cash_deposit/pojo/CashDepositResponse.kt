package com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo


data class CashDepositResponse(
    val receipt: Receipt
)

data class Receipt(
    val `data`: CashDepositData,
    val error: String,
    val status: Int
)

data class CashDepositData(
    val ACC_NO: String,
    val CURRENT_BALANCE: String,
    val DEPOSIT_AMOUNT: String,
    val DEPOSIT_DATE: String,
    val MOBILE: String,
    val NAME: String,
    val OLD_BALANCE: String,
    val TRAN_ID: String,
    val status: String
)