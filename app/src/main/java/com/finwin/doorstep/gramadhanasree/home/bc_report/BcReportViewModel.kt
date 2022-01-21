package com.finwin.doorstep.gramadhanasree.home.bc_report

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BcReportViewModel : ViewModel() {

    var mAction: MutableLiveData<Int> = MutableLiveData()

    fun clickDailyReport(view: View)
    {
        mAction.value= BcReportFragment.CLICK_DAILY_REPORT
    }

    fun clickDateToDSAteReport(view: View)
    {
        mAction.value=BcReportFragment.CLICK_DATE_TO_DATE_REPORT
    }
}