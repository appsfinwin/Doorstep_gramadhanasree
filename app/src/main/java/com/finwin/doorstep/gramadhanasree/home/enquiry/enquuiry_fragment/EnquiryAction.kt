package com.finwin.doorstep.gramadhanasree.home.enquiry.enquuiry_fragment

class EnquiryAction {
    companion object{
        public var DEFAULT: Int = -1;
        public var CLICK_ACCOUNT_STATUS: Int = 1;
        public var CLICK_MINI_STATEMENT: Int = 2;
        public var CLICK_BALANCE_ENQUIRY: Int = 3;

    }
    var action: Int? = null

    constructor(action: Int?) {
        this.action = action
    }
}