package com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.pojo.GroupAccountData


class GroupSearchItemViewmodel(
    groupAccountData: GroupAccountData,
    mAction: MutableLiveData<JlgAction>
): BaseObservable() {

    var mAction: MutableLiveData<JlgAction> = mAction
    var   groupAccountData: GroupAccountData = groupAccountData
    var groupName= groupAccountData.JLG_GroupName
    var groupCode= groupAccountData.JLG_GroupCode
    var accountNumber= groupAccountData.Ln_GlobalAccNo
    var schemeName= groupAccountData.Scheme
    var schemeCode= groupAccountData.SchemeCode
    var schemeCategory= groupAccountData.SchemeCategory
    var branchCode= groupAccountData.BranchCode

    public fun clickGroupAccount(view: View)
    {
        mAction.value= JlgAction(JlgAction.JLG_CLICK_GROUP_ACCOUNT,groupAccountData)
    }

}