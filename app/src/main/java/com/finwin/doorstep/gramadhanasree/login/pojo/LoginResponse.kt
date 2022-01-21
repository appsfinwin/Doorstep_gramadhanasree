package com.finwin.doorstep.gramadhanasree.login.pojo

data class LoginResponse(
    val user: User
)

data class User(
    val `data`: Data,
    val error: String
)

data class Data(
    val BRANCH_ID: String,
    val BRANCH_NAME: String,
    val USER_ID: String,
    val USER_NAME: String
)