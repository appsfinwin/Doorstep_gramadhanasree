package com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.action

import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo.GetAccountHolderResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.pojo.CashWithdrawalResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.pojo.OtpResponse

class CashWithdrawalAction {

    companion object{
        public var DEFAULT: Int = -1;
        public var API_ERROR: Int = 1;
        public var CLICK_SEARCH: Int = 2;
        public var GET_ACCOUNT_HOLDER_SUCCESS: Int = 3;
        public var CLICK_SUBMIT: Int = 4;
        public var CLICK_CASH_WITHDRAWAL: Int = 5;
        public var OTP_SUCCESS: Int = 6;

    }
    var action: Int? = null
    lateinit var error:String
    lateinit var getAccountHolderResponse: GetAccountHolderResponse
    lateinit var otpResponse : OtpResponse
    lateinit var cashWithdrawalResponse: CashWithdrawalResponse



    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, getAccountHolderResponse: GetAccountHolderResponse) {
        this.action = action
        this.getAccountHolderResponse = getAccountHolderResponse
    }

    constructor(action: Int?, otpResponse: OtpResponse) {
        this.action = action
        this.otpResponse = otpResponse
    }

    constructor(action: Int?, cashWithdrawalResponse: CashWithdrawalResponse) {
        this.action = action
        this.cashWithdrawalResponse = cashWithdrawalResponse
    }


}