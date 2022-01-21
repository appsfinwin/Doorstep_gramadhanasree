package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.action

import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo.GetLoanNumbers
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo.GetSchemes
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo.SearchLoanData

class SearchLoanAction {
    companion object {

        public var DEFAULT: Int = -1;
        public var CLICK_SUBMIT: Int = 1;
        public var CLICK_CANCEL: Int = 2;
        public var API_ERROR: Int = 3;
        public var SEARCH_SUCCESS: Int = 4;
        public var GET_SCHEME_SUCCESS: Int = 5;
        public var CLICK_ACCOUNT: Int = 6;
    }

    var action: Int? = null
    var error: String? = ""
    lateinit var getLoanNumbersRespponse : GetLoanNumbers
    lateinit var getLoanSchemes: GetSchemes
    lateinit var searchLoanData: SearchLoanData




    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, error: String?) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, getLoanNumbersRespponse: GetLoanNumbers) {
        this.action = action
        this.getLoanNumbersRespponse = getLoanNumbersRespponse
    }

    constructor(action: Int?, getLoanSchemes: GetSchemes) {
        this.action = action
        this.getLoanSchemes = getLoanSchemes
    }

    constructor(action: Int?, searchLoanData: SearchLoanData) {
        this.action = action
        this.searchLoanData = searchLoanData
    }


}