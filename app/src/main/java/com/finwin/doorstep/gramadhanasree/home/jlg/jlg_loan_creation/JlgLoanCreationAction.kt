package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.pojo.GetLoanPeriodResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.CreateJlGLoanResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.GetJlgProductResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.CodeMasterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Scheme

class JlgLoanCreationAction {
    companion object{
        const val  DEFAULT =-1
        const val  GET_LOAN_PERIOD_SUCCESS=1
        const val  API_ERROR=2
        const val  CODE_MASTERS_SUCCESS=3
        const val CHANGE_SCHEME_CODE=4
        const val GET_JLG_PRODUCTS_SUCCESS = 5
        const val CREATE_JLG_LOAN_SUCCESS = 6
        const val JLG_CLEAR_DATA: Int = 7
        const val JLG_PASS_AMOUNT: Int = 8
    }

    var action: Int? = null
    var amount: String? = null
    var error: String ? = null
     var getLoanPeriodResponse : GetLoanPeriodResponse? = null
     var codeMasterResponse: CodeMasterResponse? = null
     var selectedScheme: Scheme? = null
     var getJlgProductResponse: GetJlgProductResponse? = null
     var createJlGLoanResponse: CreateJlGLoanResponse? = null

    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int, getLoanPeriodResponse: GetLoanPeriodResponse) {
        this.action = action
        this.getLoanPeriodResponse = getLoanPeriodResponse
    }

    constructor(action: Int, codeMasterResponse: CodeMasterResponse) {
        this.action = action
        this.codeMasterResponse = codeMasterResponse
    }

    constructor(action: Int, selectedScheme: Scheme) {
        this.action = action
        this.selectedScheme = selectedScheme
    }

    constructor(action: Int, getJlgProductResponse: GetJlgProductResponse) {
        this.action = action
        this.getJlgProductResponse = getJlgProductResponse
    }

    constructor(action: Int, createJlGLoanResponse: CreateJlGLoanResponse) {
        this.action = action
        this.createJlGLoanResponse = createJlGLoanResponse
    }

    constructor(action: Int?, amount: String?) {
        this.action = action
        this.amount = amount
    }


}