package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists.pojo.PendingData

class RowPendingListViewModel(
    var pendingList: List<PendingData>,
    var position: Int,
    var mAction: MutableLiveData<JlgAction>
) : BaseObservable() {


    var loanType: String=   if (pendingList[position].loanType == null) "nil" else pendingList[position].loanType
    var branchName: String = if (pendingList[position].branch == null) "nil" else pendingList[position].branch
    var periodType : String = if (pendingList[position].periodType == null) "nil" else pendingList[position].periodType
    var purpose : String = if (pendingList[position].purpose == null) "nil" else pendingList[position].purpose
    var subPurpose: String =if (pendingList[position].subPurpose == null) "nil" else pendingList[position].subPurpose
    var groupName : String = if (pendingList[position].groupName == null) "nil" else pendingList[position].groupName
    var accountNumber : String = if (pendingList[position].accountNumber == null) "nil" else pendingList[position].accountNumber

    fun clickDelete(view:View)
    {
        mAction.value= JlgAction(JlgAction.CLICK_REMOVE_ACCOUNT,pendingList[position])
    }

}