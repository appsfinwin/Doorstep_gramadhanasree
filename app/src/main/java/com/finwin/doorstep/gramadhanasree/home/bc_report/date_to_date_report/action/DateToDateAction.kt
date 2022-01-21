package com.finwin.doorstep.gramadhanasree.home.bc_report.date_to_date_report.action

import com.finwin.doorstep.gramadhanasree.home.bc_report.daily_report.pojo.BcReportResponse


class DateToDateAction {
    companion object{
        public var DEFAULT: Int = -1
        public var API_ERROR: Int = 1
        public var BC_REPORT_SUCCESS: Int = 2

    }
    var action: Int? = null
    lateinit var error:String
    lateinit var bcReportResponse: BcReportResponse


    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, bcReportResponse: BcReportResponse) {
        this.action = action
        this.bcReportResponse = bcReportResponse
    }

    constructor(action: Int?) {
        this.action = action
    }


}