package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.general_details

import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.CodeMasterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Scheme

class JlgLoanGeneralDetailsAction {

    companion object{
        const val DEFAULT=-1
        const val CODE_MASTERS_SUCCESS=1
        const val API_ERROR=2
        const val CLICK_NEXT=3
        const val SELECT_SCHEME=4
    }

    var action: Int? = null
    var position: Int? = null
    var error: String = ""
    lateinit var codeMasterResponse: CodeMasterResponse
    lateinit var selectedScheme: Scheme

    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, codeMasterResponse: CodeMasterResponse) {
        this.action = action
        this.codeMasterResponse = codeMasterResponse
    }

    constructor(action: Int?, selectedScheme: Scheme) {
        this.action = action
        this.selectedScheme = selectedScheme
    }


}