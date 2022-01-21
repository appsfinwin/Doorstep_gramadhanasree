package com.finwin.doorstep.gramadhanasree.home.jlg.search_member.adapter

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.action.SearchMemberAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.pojo.MemberData

class SearchMemberRowViewModel(
    memberData: MemberData,
    mAction: MutableLiveData<SearchMemberAction>,
    testLiveData: MutableLiveData<String>,
    position:Int
) : BaseObservable() {

    var testLiveData: MutableLiveData<String> = testLiveData
    var position = position
    var memberData: MemberData= memberData
    var  mAction: MutableLiveData<SearchMemberAction> = mAction
    var memberName : String= memberData.customerName
    var customerId : String= memberData.customerId
    var accountNumber : String= memberData.accountNumber
    var phoneNumber : String= memberData.mobile

    fun clickMember(view : View)
    {
        mAction.value= SearchMemberAction(SearchMemberAction.CLICK_MEMBER,memberData)
    }
    fun onTextChanged(text: CharSequence?) {
        // TODO do something with text
      // testLiveData.value=text.toString()

        mAction.value= SearchMemberAction(SearchMemberAction.CHANGE_DATA,position,text.toString())
    }
}