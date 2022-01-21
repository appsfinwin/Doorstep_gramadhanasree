package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.pojo

data class GetLoanPeriodResponse(
    val receipt: Receipt
)

data class Receipt(
    val  status: String,
    val `data`: LoanPeriodData,
    val error: Error
)

data class LoanPeriodData(
    val DD: List<DD>,
    val MM: List<DD>,
    val WW: List<DD>
)

data class DD(
    val Ln_IntRate: String,
    val Ln_PenalRate: String,
    val Ln_Period: String,
    val Ln_PeriodType: String
)


data class Error(
    val msg: String
)

