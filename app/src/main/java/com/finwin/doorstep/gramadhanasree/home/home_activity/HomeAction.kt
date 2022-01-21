package com.finwin.doorstep.gramadhanasree.home.home_activity

class HomeAction {

    companion object {

        const val DEFAULT: Int = -1
        const val CLICK_TRANSACTION: Int = 1
        const val CLICK_ENQUIRY: Int = 2
        const val CLICK_BC_REPORT: Int = 3
        const val CLICK_AGENT: Int = 4
        const val CLICK_LOGOUT: Int = 5
        const val CLICK_JLG_LOAN: Int = 6
        const val CLICK_CUSTOMER_CREATION: Int = 7
        const val CLICK_JLG: Int = 8

    }

    var action: Int? = null

    constructor(action: Int) {
        this.action = action
    }
}