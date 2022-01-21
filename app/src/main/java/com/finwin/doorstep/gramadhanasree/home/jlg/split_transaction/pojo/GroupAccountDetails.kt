package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo

import com.google.gson.annotations.SerializedName

data class GroupAccountDetails(
    val dat: ArrayList<Dat>,
    val `data`: ArrayList<Data>,
    val status: String,
    val msg: String
)

data class Dat(
    @SerializedName("Account Number")
    val accountNumber: String,

    @SerializedName("Customer ID")
    val CustomerID: String,

    @SerializedName("Customer Name")
    val CustomerName: String,
    val Interest: String,
    var IsClosing: String,

    @SerializedName("Penal Interest")
    val PenalInterest: String,

    @SerializedName("Principal Balance")
    val PrincipalBalance: String,

    @SerializedName("Principal Due")
    val PrincipalDue: String,
    var Remittance: String,

    @SerializedName("Total Interest")
    val TotalInterest: String,

//    val AccNo: String,
//    val Amount: String,
//    val CustId: String,
//    val Name: String
)

data class Data(
    @SerializedName("Account No")
    val AccountNo: String,
    val Branch: String,
    val Center: String,
    val Group: String,
    val Ln_DueDate: String,
    val Ln_IntRate: String,
    val Ln_LoanAmount: String,
    val Ln_LoanDate: String,
    val Sch_Code: String,
    val Scheme: String,
    val Type: String
)