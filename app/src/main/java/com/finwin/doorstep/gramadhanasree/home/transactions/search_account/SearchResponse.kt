package com.finwin.doorstep.gramadhanasree.home.transactions.search_account

data class SearchResponse(
    val customer_list: CustomerList

)

data class CustomerList(
    val `data`: List<SearchData>,
    val error: String
)

data class SearchData(
    val ACC_NO: String,
    val CUST_ID: String,
    val CUST_NAME: String,
    val MOBILE: String,
    val REFNO: String
)