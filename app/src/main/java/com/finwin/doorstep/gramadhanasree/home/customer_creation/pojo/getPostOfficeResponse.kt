package com.finwin.doorstep.gramadhanasree.home.customer_creation.pojo

data class getPostOfficeResponse(
    val Message: String,
    val PostOffice: List<PostOffice>,
    val Status: String
)

data class PostOffice(
    val BranchType: String,
    val Circle: String,
    val Country: String,
    val DeliveryStatus: String,
    val Description: String,
    val District: String,
    val Division: String,
    val Name: String,
    val Region: String,
    val State: String,
    val Taluk: String
)