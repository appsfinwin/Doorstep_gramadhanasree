package com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.pojo

data class CashWithdrawalResponse (  val receipt: Receipt){

    data class Receipt(
        val `data`: CashWithdrawalData
    )

    data class CashWithdrawalData(
      //  val ACC_NO: String,
        val CURRENT_BALANCE: String,
        val WITHDRAWAL_AMOUNT: String,
        val WITHDRAWAL_DATE: String,
        val MOBILE: String,
        //val NAME: String,
        val OLD_BALANCE: String,
        val TRAN_ID: String
        //val status: String
    )
}