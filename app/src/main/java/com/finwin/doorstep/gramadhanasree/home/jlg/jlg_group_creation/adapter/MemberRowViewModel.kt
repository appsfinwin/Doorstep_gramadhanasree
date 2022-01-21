package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.adapter

import android.view.View
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.GroupCreationAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation.pojo.SelectedMember


class MemberRowViewModel(
    memberData: SelectedMember,
    mAction: MutableLiveData<GroupCreationAction>
) {
    var memberData: SelectedMember = memberData
    var mAction: MutableLiveData<GroupCreationAction> = mAction
    var memberName: String? = memberData.customerName
    var customerId: String? = memberData.customerId
    var accountNumber: String? = memberData.accountNumber
    var phoneNumber: String? = memberData.customerMobile

    fun clickMember(view:View)
    {
        mAction.value= GroupCreationAction(GroupCreationAction.CLICK_MEMBER,memberData)
    }

    fun clickMemberDelete(view:View)
    {
        mAction.value= GroupCreationAction(GroupCreationAction.CLICK_MEMBER_DELETE,memberData)
    }
}