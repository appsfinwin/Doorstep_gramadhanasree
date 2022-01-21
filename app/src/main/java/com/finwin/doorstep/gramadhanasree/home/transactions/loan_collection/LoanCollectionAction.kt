package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection

import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo.GetLoanAccountHolderResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo.LoanCollectionResponse


class LoanCollectionAction {
    companion object{
        const val DEFAULT = -1;
        const val GET_LOAN_ACCOUNT_HOLDER_SUCCESS = 1;
        const val API_ERROR = 2;
        const val CLICK_LOAN_COLLECTION = 3;
        const val LOAN_COLLECTION_SUCCESS = 4;
    }

    var loanAccountHolderResponse : GetLoanAccountHolderResponse? = null
    var loanCollectionResponse : LoanCollectionResponse?= null
    //lateinit var dummyData: DummyData

    var action: Int? = null
    var error: String? = ""

    constructor(action: Int?, error: String?) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?) {
        this.action = action
    }

    constructor( action: Int?,loanAccountHolderResponse: GetLoanAccountHolderResponse) {
        this.loanAccountHolderResponse = loanAccountHolderResponse
        this.action = action
    }

    constructor( action: Int? , loanCollectionResponse: LoanCollectionResponse?) {
        this.loanCollectionResponse = loanCollectionResponse
        this.action = action
    }


}