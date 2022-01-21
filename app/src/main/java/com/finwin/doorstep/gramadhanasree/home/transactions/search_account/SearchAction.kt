package com.finwin.doorstep.gramadhanasree.home.transactions.search_account

class SearchAction {

    companion object{

        public val DEFAULT:Int=-1
        public val CLICK_ACCOUNT:Int=1
        public val SEARCH_ACCOUNT_SUCCESS:Int=2
        public val API_ERROR:Int=3
        public val CLICK_CANCEL:Int=4
    }

    var action:Int = 0
    lateinit var error:String
    lateinit var searchAccountResponse: SearchResponse
    lateinit var searchData: SearchData


    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, searchAccountResponse: SearchResponse) {
        this.action = action
        this.searchAccountResponse = searchAccountResponse
    }

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int, searchData: SearchData) {
        this.action = action
        this.searchData = searchData
    }
}