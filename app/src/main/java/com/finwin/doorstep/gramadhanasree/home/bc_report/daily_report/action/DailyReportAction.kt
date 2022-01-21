package com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report.action


import com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report.pojo.BcReportResponse


class DailyReportAction {
    companion object{
        public var DEFAULT: Int = -1;
        public var API_ERROR: Int = 1;
        public var CLICK_SEARCH: Int = 4;
        public var BALANCE_ENQUIRY_SUCCESS: Int = 5;
        public var DAILY_REPORT_SUCCESS: Int = 6;

    }
    var action: Int? = null
    lateinit var error:String
    lateinit var bcReportResponse: BcReportResponse

    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, bcReportResponse: BcReportResponse) {
        this.action = action
        this.bcReportResponse = bcReportResponse
    }


}