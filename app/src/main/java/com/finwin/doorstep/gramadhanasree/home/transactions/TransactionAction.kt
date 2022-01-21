package com.finwin.doorstep.gramadhanasree.home.transactions

class TransactionAction {
    companion object{
        public var DEFAULT: Int = -1;
        public var CLICK_CASH_DEPOSIT: Int = 1;
        public var CLICK_CASH_WITHDRAWAL: Int = 2;
        public var CLICK_TRANSFER: Int = 3;
        public var CLICK_NEFT: Int = 4;
        public var CLICK_LOAN_COLLECTION: Int = 5;
        public var CLICK_LOAN_CLOSING: Int = 6;

    }
    var action: Int? = null

    constructor(action: Int?) {
        this.action = action
    }
}