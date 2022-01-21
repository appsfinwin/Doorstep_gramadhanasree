package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_remittance_details

import android.view.View
import androidx.databinding.ObservableArrayList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Dat

class ClosingRemittanceDetailsViewModel : ViewModel() {
    var mAction: MutableLiveData<JlgAction>
    public var accountListData: ObservableArrayList<Dat> = ObservableArrayList();

    init {
        mAction = MutableLiveData()
    }

    fun clickNext(view: View) {
        mAction.value = JlgAction(JlgAction.CLICK_NEXT_FROM_REMITTANCE_DETAILS)
    }

    fun clickPrevious(view: View) {
        mAction.value = JlgAction(JlgAction.CLICK_PREVIOUS_FROM_REMITTANCE_DETAILS)
    }

    fun setAccountData(it: List<Dat>?) {
        accountListData.clear()

        if (it != null) {
            for (i in it.indices) {
                accountListData.add(it[i])
            }
        }
    }
}