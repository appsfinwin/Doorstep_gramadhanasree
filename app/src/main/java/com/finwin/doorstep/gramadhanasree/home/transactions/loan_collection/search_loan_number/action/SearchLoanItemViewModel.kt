package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.action

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.pojo.SearchLoanData
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.action.SearchLoanAction

class SearchLoanItemViewModel(


        searchData: SearchLoanData,
        mAction: MutableLiveData<SearchLoanAction>
    ) : BaseObservable() {
        var mAction=mAction
        var searchData: SearchLoanData =searchData
        fun setData(searchData: SearchLoanData) {
            this.searchData=searchData
        }
        val name:String = searchData.custName
        val customeriD:String = searchData.custId
        val accountNumber:String = searchData.loanAccountNumber
        val schemeName:String = searchData.schemeName


        fun clickAccount(view: View)
        {
            mAction.value=
                SearchLoanAction(
                    SearchLoanAction.CLICK_ACCOUNT,searchData
                )
        }
}