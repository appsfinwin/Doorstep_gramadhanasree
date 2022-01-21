package com.finwin.doorstep.gramadhanasree.home.jlg.search_group.action

import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.GetCenterByBranchResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.GetSearchGroupResponse
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.SearchGroupData

class SearchGroupAction {
    companion object {

        const val DEFAULT = -1
        const val API_ERROR = 1
        const val SEARCH_GROUP_SUCCESS = 2
        const val GET_CENTER_GY_BRANCH_SUCCESS = 3
        const val CLICK_GROUP = 4

    }

    var action: Int = 0
    var error: String = ""
    lateinit var searchGroupResponse: GetSearchGroupResponse
    lateinit var getCenterByBranchResponse: GetCenterByBranchResponse
    lateinit var searchGroupData: SearchGroupData


    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(
        action: Int,
        searchGroupResponse: GetSearchGroupResponse
    ) {
        this.action = action
        this.searchGroupResponse = searchGroupResponse
    }

    constructor(action: Int, getCenterByBranchResponse: GetCenterByBranchResponse) {
        this.action = action
        this.getCenterByBranchResponse = getCenterByBranchResponse
    }

    constructor(action: Int, searchGroupData: SearchGroupData) {
        this.action = action
        this.searchGroupData = searchGroupData
    }




}