package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.adapter

import androidx.databinding.BaseObservable
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo.Receiptdetail

class LoanRowViewModel(var listAmount: Receiptdetail) : BaseObservable() {

    var balance= listAmount.balance
    var currentReceipt= listAmount.currentReceipt
    var overdue=  listAmount.overdue
    var particular=  listAmount.particular
    var totalReceived=  listAmount.totalReceived

}