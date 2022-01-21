package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.pojo

data class MiniStatementResponse(
    val mini_statement: MiniStatement
)

data class MiniStatement(
    val `data`: MiniStatementData
)

data class MiniStatementData(
    val ACC_NO: String,
    val CURRENT_BALANCE: String,
    val MOBILE: String,
    val NAME: String,
    val TRANSACTONS: List<TRANSACTONS>
)
data class MiniStatementProfile(
    val ACC_NO: String,
    val CURRENT_BALANCE: String,
    val MOBILE: String,
    val NAME: String
)

data class TRANSACTONS(
    val BALANCE: String,
    val DORC: String,
    val PARTICULAR: String,
    val TRAN_TYPE: String,
    val TXN_AMOUNT: String,
    val TXN_DATE: String,
    val TXN_ID: String
)