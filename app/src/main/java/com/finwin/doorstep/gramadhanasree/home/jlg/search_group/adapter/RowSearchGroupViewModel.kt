package com.finwin.doorstep.gramadhanasree.home.jlg.search_group.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.action.SearchGroupAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.SearchGroupData

class RowSearchGroupViewModel(var groupData: SearchGroupData, var mAction: MutableLiveData<SearchGroupAction>) : BaseObservable() {

    var groupName = groupData.groupName
    var groupCode = groupData.groupCode
    var branchCode = groupData.branchCode
    var centerCode = groupData.centerCode

    fun clickGroup(view: View)
    {
        mAction.value = SearchGroupAction(SearchGroupAction.CLICK_GROUP, groupData)
    }
}