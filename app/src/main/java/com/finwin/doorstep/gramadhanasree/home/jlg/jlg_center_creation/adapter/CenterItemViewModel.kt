package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.pojo.CenterData

class CenterItemViewModel(
    centerData: CenterData,
    mAction: MutableLiveData<JlgAction>
) : BaseObservable() {

    var centerData: CenterData = centerData
    var mAction: MutableLiveData<JlgAction> = mAction
    var branchName :String= this.centerData.BranchName
    var centerCode :String= this.centerData.CenterCode
    var centerName :String= this.centerData.CenterName

    public fun clickRemove(view: View)
    {
        mAction.value= JlgAction(JlgAction.JLG_CLICK_DELETE_CENTER,centerData)
    }

    public fun clickEdit(view: View)
    {
        mAction.value= JlgAction(JlgAction.JLG_CLICK_EDIT_CENTER,centerData)
    }

}