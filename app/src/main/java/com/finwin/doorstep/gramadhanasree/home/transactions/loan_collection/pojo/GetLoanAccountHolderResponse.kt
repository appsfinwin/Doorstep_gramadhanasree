package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo

import com.google.gson.annotations.SerializedName

data class GetLoanAccountHolderResponse(
        @SerializedName("ACNO")
    val accountNumber: String,

        @SerializedName("CUST_NO")
    val customerNumber: String,

        @SerializedName("InterestRate")
    val interestRate: String,

        @SerializedName("LoanAmount")
    val loanAmount: String,

        @SerializedName("LoanDate")
    val loanDate: String,

        @SerializedName("LoanNo")
    val loanNumber: String,

        @SerializedName("LoanPeriod")
    val loanPeriod: String,

        @SerializedName("LoanType")
    val loanType: String,

        @SerializedName("Member_No")
    val memberNumber: String,

        @SerializedName("Name")
    val name: String,

        @SerializedName("Receiptdetails")
    val receiptDetails: List<Receiptdetail>,

        @SerializedName("Status")
    val status: String,
        val error: String
//    @SerializedName("Status")
//    val error: String
)

data class Receiptdetail(

    @SerializedName("Balance")
    val balance: String,

    @SerializedName("Current Receipt")
    val currentReceipt: String,

    @SerializedName("Overdue")
    val overdue: String,

    @SerializedName("Particular")
    val particular: String,

    @SerializedName("Total Received")
    val totalReceived: String
)
