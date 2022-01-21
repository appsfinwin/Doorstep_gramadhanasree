package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.pojo.GetLoanPeriodResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.CreateJlGLoanResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.CodeMasterResponse

class      JlgLoanDetailsAction {
    companion object
    {
        const val DEFAULT=-1
        const val API_ERROR=1
        const val GET_REF_CODES_SUCCESS=2
        const val GET_LOAN_PERIOD_SUCCESS=3
        const val CREATE_JLG_LOAN_SUCCESS=4
        const val CLICK_SUBMIT=5
        const val CLICK_SEARCH_ACCOUNT=6
        const val CLICK_PREVIOUS=7
    }


    var action : Int = 0
    var error : String=""
    lateinit var codeMastersResponse: CodeMasterResponse
    lateinit var getLoanPeriodResponse: GetLoanPeriodResponse
    lateinit var createJlgLoanResponse: CreateJlGLoanResponse

    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int, codeMastersResponse: CodeMasterResponse) {
        this.action = action
        this.codeMastersResponse = codeMastersResponse
    }

    constructor(action: Int, getLoanPeriodResponse: GetLoanPeriodResponse) {
        this.action = action
        this.getLoanPeriodResponse = getLoanPeriodResponse
    }

    constructor(action: Int, createJlgLoanResponse: CreateJlGLoanResponse) {
        this.action = action
        this.createJlgLoanResponse = createJlgLoanResponse
    }


}