package com.finwin.doorstep.gramadhanasree.home.jlg.search_center

import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.pojo.CenterSearchData
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.pojo.GetSearchCenterResponse

class SearchCenterAction {

    companion object{
        const val DEFAULT= -1;
        const val SEARCH_CENTER_SUCCESS= 1;
        const val API_ERROR= 2;
        const val CLICK_CENTER= 3;
    }

    var action: Int? =0
    var error: String= ""
    lateinit var getSearchCenterResponse : GetSearchCenterResponse
    lateinit var centerData: CenterSearchData

    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }



    constructor(action: Int?, centerData: CenterSearchData) {
        this.action = action
        this.centerData = centerData
    }

    constructor(action: Int?, getSearchCenterResponse: GetSearchCenterResponse) {
        this.action = action
        this.getSearchCenterResponse = getSearchCenterResponse
    }


}