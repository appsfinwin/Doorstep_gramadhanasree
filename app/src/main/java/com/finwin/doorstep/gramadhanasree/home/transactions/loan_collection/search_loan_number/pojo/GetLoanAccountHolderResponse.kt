package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo

import com.google.gson.annotations.SerializedName

data class GetLoanAccountHolderResponse(
    @SerializedName("account")
    val customerList: LoanAccount
//
//    @SerializedName("Member_No")
//    val memberNo: String,
//
//    @SerializedName("CUST_NO")
//    val custId: String,
//
//    @SerializedName("Name")
//    val name: String,
//
//    @SerializedName("LoanNo")
//    val loanNo: String,
//
//    @SerializedName("LoanDate")
//    val loanDate: String,
//
//    @SerializedName("InterestRate")
//    val interestDate: String,
//
//    @SerializedName("LoanAmount")
//    val loanAmount: String,
//
//    @SerializedName("LoanType")
//    val loanType: String,
//
//    @SerializedName("LoanPeriod")
//    val loanPeriod: String,
//
//    @SerializedName("Status")
//    val loanStatus: String

)

data class LoanAccount(
        val `receiptdetails`: List<Receiptdetails>,
        @SerializedName("error")
    val error: String,

        @SerializedName("closed")
    val closed: String
)

data class Receiptdetails(


    @SerializedName("Particular")
    val particular: String,

    @SerializedName("Total Received")
    val totalReceived: String,

    @SerializedName("Balance")
    val balance: String,

    @SerializedName("Overdue")
    val overdue: String,

    @SerializedName("Current Receipt")
    val currentReceipt: String

)