package com.finwin.doorstep.gramadhanasree.home.jlg.search_center.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.SearchCenterAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.pojo.CenterSearchData

class RowSearchGroupViewModel(data: CenterSearchData,
                              mAction: MutableLiveData<SearchCenterAction>): BaseObservable()
{
    var centerData: CenterSearchData = data
    var centerName= data.centerName
    var centerCode= data.centerCode
    var branchName= data.branchName
    var branchCode= data.branchCode
    var makerId= data.makerId
    var makingTime= data.makingTime
    var mAction= mAction

    fun clickCenter(view: View)
    {
        mAction.value= SearchCenterAction(SearchCenterAction.CLICK_CENTER,centerData)
    }
}