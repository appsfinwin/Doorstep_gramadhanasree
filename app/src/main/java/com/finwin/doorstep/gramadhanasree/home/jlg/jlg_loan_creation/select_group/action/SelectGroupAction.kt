package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.action

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.Member
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.GetGroupSelectResponse

class SelectGroupAction {
    companion object {
        const val DEFAULT = -1
        const val CLICK_NEXT = 1
        const val CLICK_SEARCH = 2
        const val API_ERROR = 3
        const val GET_GROUP_SELECT_SUCCESS = 4
        const val CHANGE_DATA = 5

    }

    var action: Int = 0
    var error: String = ""
    lateinit var getGroupSelectResponse: GetGroupSelectResponse
    lateinit var member: Member


    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int, getGroupSelectResponse: GetGroupSelectResponse) {
        this.action = action
        this.getGroupSelectResponse = getGroupSelectResponse
    }

    constructor(action: Int, member: Member) {
        this.action = action
        this.member = member
    }


}