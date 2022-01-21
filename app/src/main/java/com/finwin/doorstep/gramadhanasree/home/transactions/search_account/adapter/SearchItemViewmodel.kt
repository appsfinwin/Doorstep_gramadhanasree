package com.finwin.doorstep.gramadhanasree.home.transactions.search_account.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchAction
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchData
import com.finwin.doorstep.gramadhanasree.databinding.ItemSearchLayoutBinding

class SearchItemViewmodel(
    binding: ItemSearchLayoutBinding,
    searchData: SearchData,
    mAction: MutableLiveData<SearchAction>
) : BaseObservable() {
    var mAction=mAction
     var searchData: SearchData =searchData
    fun setData(searchData: SearchData) {
        this.searchData=searchData
    }
        val name:String = searchData.CUST_NAME
        val customeriD:String = searchData.CUST_ID
        val accountNumber:String = searchData.ACC_NO
        val mobile:String = searchData.MOBILE

    fun clickAccount(view:View)
    {
        mAction.value=
            SearchAction(
                SearchAction.CLICK_ACCOUNT,searchData
            )
    }

}