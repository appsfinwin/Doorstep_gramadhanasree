package com.finwin.doorstep.gramadhanasree.home.customer_creation.pojo

data class CustomerCreationResponse(
    val `data`: CustomerCreationData
)

data class CustomerCreationData(
    val Table1: List<Table1>
)

data class Table1(
    val ReturnID: String,
    val ReturnMessage: String,
    val ReturnName: String,
    val ReturnStatus: String
)