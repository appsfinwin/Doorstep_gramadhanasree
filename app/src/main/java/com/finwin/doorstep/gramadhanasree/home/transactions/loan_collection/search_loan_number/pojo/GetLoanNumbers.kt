package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo

import com.google.gson.annotations.SerializedName

data class GetLoanNumbers(
        @SerializedName("CustomerList")
        val customerList: CustomerList
)

data class CustomerList(
        val `data`: List<SearchLoanData>,
        val error: String
)

data class SearchLoanData(
        @SerializedName("Cust_Id")
        val custId: String,

        @SerializedName("Cust_Name")
        val custName: String,

        @SerializedName("Ln_GlobalAccNo")
        val loanAccountNumber: String,

        @SerializedName("Sch_Name")
        val schemeName: String,

        @SerializedName("Sch_Code")
        val schemeCode: String
)