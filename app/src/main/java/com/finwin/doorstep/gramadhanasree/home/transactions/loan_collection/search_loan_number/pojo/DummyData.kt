package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo

data class DummyData(
    val account: Account
)

data class Account(
    val error: Error
)

data class Error(
    val msg: String
)