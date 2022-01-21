package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.adapter

import androidx.databinding.BaseObservable
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.pojo.MiniStatementProfile

class ProfileViewmodel(get: MiniStatementProfile) : BaseObservable() {

    var miniStatementProfile: MiniStatementProfile =get

    var name: String=miniStatementProfile.NAME
    var mobile: String=miniStatementProfile.MOBILE
    var accountNumber: String=miniStatementProfile.ACC_NO
    var currentBalance: String=miniStatementProfile.CURRENT_BALANCE

}