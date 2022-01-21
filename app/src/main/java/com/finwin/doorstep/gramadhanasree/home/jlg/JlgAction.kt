package com.finwin.doorstep.gramadhanasree.home.jlg

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.pojo.CenterData
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.pojo.JlgCreateCenterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.pojo.getjLgCenterResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.pojo.JlgPendingListResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.pojo.PendingData
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.pojo.RemoveAccountResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.pojo.GroupAccountData
import com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.pojo.SearchGroupAccountResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.*
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.CodeMasterResponse

class JlgAction {
    companion object {

        const val DEFAULT: Int = -1
        const val API_ERROR: Int = 1
        const val JLG_GET_GROUP_ACCOUNT_SUCCESS: Int = 2
        const val JLG_CLICK_GROUP_ACCOUNT: Int = 3
        const val JLG_CODE_MASTERS_SUCCESS: Int = 4
        const val CLICK_SEARCH_GROUP: Int = 5
        const val JLG_GET_GROUP_ACCOUNT_DETAILS_SUCCESS: Int = 6
        const val CLICK_NEXT_FROM_GENERAL_DETAILS: Int = 7
        const val CLICK_SEARCH_ACCCOUNT_NUMBER: Int = 8
        const val CLICK_NEXT_FROM_REMITTANCE_DETAILS: Int = 9
        const val CLICK_PREVIOUS_FROM_REMITTANCE_DETAILS: Int = 10
        const val CLICK_PREVIOUS_FROM_OTHER_DETAILS: Int = 11
        const val SELECT_ACCOUNT: Int = 12
        const val DE_SELECT_ACCOUNT: Int = 13
        const val CLICK_DELETE: Int = 14
        const val JLG_UPDATE_CENTER: Int = 15
        const val JLG_CREATE_CENTER: Int = 16
        const val JLG_GET_CENTER_SUCCESS: Int = 17
        const val JLG_CLICK_EDIT_CENTER: Int = 18
        const val JLG_CLICK_DELETE_CENTER: Int = 19
        const val JLG_GET_GROUP_ACCOUNT_DETAILS: Int = 20
        const val ADD_CHARGES: Int = 21
        const val CLICK_NEXT_FROM_JLG_SPLIT_GENERAL_DETAILS: Int = 22
        const val CLICK_SUBMIT_FROM_JLG_SPLIT_OTHER_DETAILS: Int = 23
        const val SPLIT_TRANSACTION_SUCCESS: Int = 24
        const val JLG_CLEAR_DATA: Int = 25
        const val JLG_PENDING_LIST_SUCCESS: Int = 26
        const val REMOVE_ACCOUNT_SUCCESS: Int = 27
        const val CLICK_REMOVE_ACCOUNT: Int = 28
        const val UPDATE_ACCOUNTS_DATA: Int = 29

    }

    var action: Int? = null
    var position: Int? = null
    var error: String = ""
    var jlgCenterResponse: getjLgCenterResponse? = null
    var jlgCreateCenterResponse: JlgCreateCenterResponse? = null
    var centerData: CenterData? = null
    var groupAccountData: GroupAccountData? = null
    var searchGroupAccountResponse: SearchGroupAccountResponse? = null
    var codeMasterResponse: CodeMasterResponse?= null
    var groupAcccountDetails: GroupAccountDetails? = null
    var dat: Dat? = null
    var charges: Charges? = null
    var splitTransactionResponse: SplitTransactionResponse? = null
    var getPendingListResponse: JlgPendingListResponse? = null
    var removeAccountResponse: RemoveAccountResponse? = null
    var pendingData: PendingData? = null
    var accountsListData: List<Dat>? = null


    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, jlgCenterResponse: getjLgCenterResponse) {
        this.action = action
        this.jlgCenterResponse = jlgCenterResponse
    }

    constructor(action: Int?, jlgCreateCenterResponse: JlgCreateCenterResponse) {
        this.action = action
        this.jlgCreateCenterResponse = jlgCreateCenterResponse
    }

    constructor(action: Int?, centerData: CenterData) {
        this.action = action
        this.centerData = centerData
    }

    constructor(action: Int?, groupAccountData: GroupAccountData) {
        this.action = action
        this.groupAccountData = groupAccountData
    }

    constructor(action: Int?, searchGroupAccountResponse: SearchGroupAccountResponse) {
        this.action = action
        this.searchGroupAccountResponse = searchGroupAccountResponse
    }

    constructor(action: Int?, codeMasterResponse: CodeMasterResponse) {
        this.action = action
        this.codeMasterResponse = codeMasterResponse
    }

    constructor(action: Int?, groupAcccountDetails: GroupAccountDetails) {
        this.action = action
        this.groupAcccountDetails = groupAcccountDetails
    }

    constructor(action: Int?, dat: Dat) {
        this.action = action
        this.dat = dat
    }

    constructor(action: Int?, charges: Charges, position:Int?) {
        this.action = action
        this.charges = charges
        this.position=position
    }

    constructor(action: Int?, charges: Charges) {
        this.action = action
        this.charges = charges
    }

    constructor(action: Int?, splitTransactionResponse: SplitTransactionResponse?) {
        this.action = action
        this.splitTransactionResponse = splitTransactionResponse
    }

    constructor(action: Int?, getPendingListResponse: JlgPendingListResponse?) {
        this.action = action
        this.getPendingListResponse = getPendingListResponse
    }

    constructor(action: Int?, removeAccountResponse: RemoveAccountResponse?) {
        this.action = action
        this.removeAccountResponse = removeAccountResponse
    }

    constructor(action: Int?, pendingData: PendingData?) {
        this.action = action
        this.pendingData = pendingData
    }

    constructor(action: Int?, accountsListData: List<Dat>?) {
        this.action = action
        this.accountsListData = accountsListData
    }


}