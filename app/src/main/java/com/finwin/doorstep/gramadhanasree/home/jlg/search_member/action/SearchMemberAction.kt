package com.finwin.doorstep.gramadhanasree.home.jlg.search_member.action

import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.pojo.GetSearchMemberResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.pojo.MemberData

class SearchMemberAction {
    companion object {
        const val DEFAULT = -1
        const val CLICK_MEMBER = 1
        const val GET_MEMBER_SUCCESS = 2
        const val API_ERROR = 3
        const val CHANGE_DATA = 4

    }

    var action: Int = 0
    var position: Int = 0
    var error: String = ""
    lateinit var memberData: MemberData
    lateinit var getSearchMemberResponse: GetSearchMemberResponse

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int, memberData: MemberData) {
        this.action = action
        this.memberData = memberData
    }

    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, getSearchMemberResponse: GetSearchMemberResponse) {
        this.action = action
        this.getSearchMemberResponse = getSearchMemberResponse
    }

    constructor(action: Int, position: Int, error: String) {
        this.action = action
        this.position = position
        this.error = error
    }


}