package com.finwin.doorstep.gramadhanasree.home.home_activity

import android.view.View
import androidx.databinding.BaseObservable
import androidx.lifecycle.MutableLiveData


class NavMenuViewmodel :BaseObservable(){

    var mAction: MutableLiveData<HomeAction>

    init {
        mAction=MutableLiveData()
    }

    fun clickTransaction(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_TRANSACTION
        )
    }

    fun clickJlgLoan(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_JLG_LOAN
        )
    }

    fun clickEnquiry(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_ENQUIRY
        )
    }

    fun clickBcReport(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_BC_REPORT
        )
    }

    fun clickCustomerCreation(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_CUSTOMER_CREATION
        )
    }

    fun clickAgentManegement(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_AGENT
        )
    }

    fun clickLogout(view : View)
    {
        mAction.value= HomeAction(
            HomeAction.CLICK_LOGOUT
        )
    }



}