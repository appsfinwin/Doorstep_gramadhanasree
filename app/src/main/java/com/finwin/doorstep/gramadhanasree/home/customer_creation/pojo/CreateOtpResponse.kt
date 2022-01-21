package com.finwin.doorstep.gramadhanasree.home.customer_creation.pojo

data class CreateOtpResponse(
    val otp: Otp
)

data class Otp(
    val `data`: Int,
    val otp_id: String,
    val status: String,
    val Msg: String
)