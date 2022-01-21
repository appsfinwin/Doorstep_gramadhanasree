package com.finwin.doorstep.gramadhanasree.home.jlg

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgFragment

class JlgViewModel : ViewModel() {

    var mAction: MutableLiveData<Int> = MutableLiveData()

    public fun clickCenterCreation(view: View)
    {
        mAction.value= JlgFragment.CLICK_CENTER_CREATION

    }

    public fun clickSplitTransaction(view: View)
    {
        mAction.value= JlgFragment.CLICK_SPLIT_TRANSACTIONS

    }
    public fun clickGroupCreation(view: View)
    {
        mAction.value= JlgFragment.CLICK_GROUP_CREATION

    }

    public fun clickLoanCreation(view: View)
    {
        mAction.value= JlgFragment.CLICK_JLG_LOAN_CREATION

    }
    public fun clickSplitClosing(view: View)
    {
        mAction.value= JlgFragment.CLICK_JLG_SPLIT_CLOSING

    }

    public fun clickPendingList(view: View)
    {
        mAction.value= JlgFragment.CLICK_CLICK_PENDING_LIST

    }
}