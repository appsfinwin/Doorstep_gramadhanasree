package com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit

import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo.CashDepositResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo.GetAccountHolderResponse

class CashDepositAction {

    companion object{
        public var DEFAULT: Int = -1;
        public var API_ERROR: Int = 1;
        public var LOGIN_SUCCESS: Int = 2;
        public var LOGIN_ERROR: Int = 3;
        public var CLICK_SEARCH: Int = 4;
        public var GET_ACCOUNT_HOLDER_SUCCESS: Int = 5;
        public var CLICK_SUBMIT: Int = 6;
        public var CLICK_DEPOSIT: Int = 7;
        public var CASH_DEPOSIT_SUCCESS: Int = 8;
    }
    var action: Int? = null
    var error:String?= null
    var getAccountHolderResponse: GetAccountHolderResponse?= null
    var cashDepositResponse: CashDepositResponse?= null




    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, getAccountHolderResponse: GetAccountHolderResponse) {
        this.action = action
        this.getAccountHolderResponse = getAccountHolderResponse
    }

    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, cashDepositResponse: CashDepositResponse) {
        this.action = action
        this.cashDepositResponse = cashDepositResponse
    }
}