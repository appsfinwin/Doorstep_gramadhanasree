package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.other_details.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Charges

class ChargesViewwmodel(val chargesData: Charges, mAction: MutableLiveData<JlgAction>, position: Int) :  BaseObservable() {


    var mAction= mAction
    var position= position
    var accountNumber: String = chargesData.accountNumber
    var charge: String =  chargesData.charges
    var amount: String =  chargesData.amount

    public fun clickDelete(view: View)
    {
        mAction.value= JlgAction(JlgAction.CLICK_DELETE,chargesData,position)
    }
}