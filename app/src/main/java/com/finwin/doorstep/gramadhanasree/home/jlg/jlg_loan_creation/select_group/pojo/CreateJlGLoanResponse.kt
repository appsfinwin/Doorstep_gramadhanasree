package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo

import com.google.gson.annotations.SerializedName

data class CreateJlGLoanResponse(
    val AccountNo: String,
    val dt: List<Dt>,
    val dt1: List<Dt1>,
    val dt2: List<Dt2>,
    val msg: String,
    val status: String
)

data class Dt(
    @SerializedName("Due Date")
    val dueDate: String,

    @SerializedName("Group ID")
    val groupId: String,

    @SerializedName("Interest Rate")
    val  interestRate: String,

    @SerializedName("Loan Amount")
    val loanAmount: Int,

    @SerializedName("Loan Date")
    val loanDate: String,

    @SerializedName("Loan Type")
    val loanType: String,

    @SerializedName("Penal Interest Rate")
    val penalInterestRate: String,

    @SerializedName("Period")
    val period: String,

    @SerializedName("Period Type")
    val periodType: String,

    @SerializedName("Scheme")
    val scheme: String
)

data class Dt1(
    @SerializedName("Account No")
    val accountNumber: String,

    @SerializedName("Effective Date")
    val effectiveDate: String,
    val Gl: String,

    @SerializedName("Scheme")
    val scheme: String,

    @SerializedName("Tran Type")
    val tranType: String,

    @SerializedName("Transaction Date")
    val transactionDate: String,

    @SerializedName("Transaction No")
    val transactionNo: String,

    @SerializedName("Voucher No")
    val voucherNo: String
)

data class Dt2(
    val AccNo: String,
    val Cash: Int,
    val Displayname: String,
    val Glcode: String,
    val Sub_TranType: String,
    val Transfer: Int
)