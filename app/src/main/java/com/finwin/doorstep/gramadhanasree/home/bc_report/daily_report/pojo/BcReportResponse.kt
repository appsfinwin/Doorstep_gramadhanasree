package com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report.pojo

data class BcReportResponse(
    val bc_report: BcReport
)

data class BcReport(
    val `data`: BcReportData,
    val TXN_DATE: String,
    val error: String
)

data class BcReportData(
    val CASH_IN_HAND: String,
    val NO_OF_ACC_CREATED: String,
    val NO_OF_CHEQUES_COLLECTED: String,
    val NO_OF_CUST_CREATED: String,
    val NO_OF_DEPOSITS: String,
    val NO_OF_TRANSACTIONS: String,
    val NO_OF_TRANSFER: String,
    val NO_OF_WITHDRAWAL: String,
    val REPORT_GENERATED_DATE: String,
    val TOTAL_DEPOSITS: String,
    val TOTAL_TRANSFER: String,
    val TOTAL_WITHDRAWAL: String
)