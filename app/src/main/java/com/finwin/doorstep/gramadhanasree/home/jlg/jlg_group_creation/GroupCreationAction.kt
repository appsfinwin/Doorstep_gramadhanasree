package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.pojo.CreateGroupResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.pojo.SelectedMember

class GroupCreationAction {
    companion object
    {
        const val DEFAULT=-1
        const val CLICK_MEMBER=2
        const val CLICK_MEMBER_DELETE=3
        const val API_ERROR=4
        const val CREATE_GROUP_SUCCESS=5
    }

    var action: Int =0
    var error: String?=null
    var selectedMember: SelectedMember?=null
    var createGroupResponse: CreateGroupResponse?=null

    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int, selectedMember: SelectedMember) {
        this.action = action
        this.selectedMember = selectedMember
    }

    constructor(action: Int, createGroupResponse: CreateGroupResponse) {
        this.action = action
        this.createGroupResponse = createGroupResponse
    }


}