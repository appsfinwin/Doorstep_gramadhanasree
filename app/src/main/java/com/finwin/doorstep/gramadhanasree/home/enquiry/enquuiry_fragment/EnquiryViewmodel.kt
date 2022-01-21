package com.finwin.doorstep.gramadhanasree.home.enquiry.enquuiry_fragment

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EnquiryViewmodel : ViewModel() {

    var mAction: MutableLiveData<EnquiryAction> = MutableLiveData()

    fun clickAccountStatus(view:View)
    {
        mAction.value=
            EnquiryAction(
                EnquiryAction.CLICK_ACCOUNT_STATUS
            )
    }

    fun clickMiniStatement(view: View)
    {
        mAction.value=
            EnquiryAction(
                EnquiryAction.CLICK_MINI_STATEMENT
            )
    }

    fun clickBalanceEnquiry(view: View)
    {
        mAction.value=
            EnquiryAction(
                EnquiryAction.CLICK_BALANCE_ENQUIRY
            )
    }
}