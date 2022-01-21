package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.action

import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.pojo.MiniStatementResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo.GetAccountHolderResponse

class MiniStatementAction {
    companion object{
        public var DEFAULT: Int = -1;
        public var API_ERROR: Int = 1;
        public var CLICK_SEARCH: Int = 4;
        public var GET_ACCOUNT_HOLDER_SUCCESS: Int = 5;
        public var CLICK_SUBMIT: Int = 6;
        public var CLICK_MINI_STATEMENT: Int = 7;
        public var GET_MINI_STATEMENT_SUCCESS: Int = 8;

    }
    var action: Int? = null
    var error:String? = null
    var getAccountHolderResponse: GetAccountHolderResponse? = null
    var miniStatementResponse: MiniStatementResponse? = null



    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, getAccountHolderResponse: GetAccountHolderResponse) {
        this.action = action
        this.getAccountHolderResponse = getAccountHolderResponse
    }

    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, miniStatementResponse: MiniStatementResponse) {
        this.action = action
        this.miniStatementResponse = miniStatementResponse
    }


}