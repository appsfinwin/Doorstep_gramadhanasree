package com.finwin.doorstep.gramadhanasree.home.transactions

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
class TransactionsViewmodel :ViewModel() {
    var mAction:MutableLiveData<TransactionAction> = MutableLiveData()

    fun clickCashDeposit(view:View)
    {
        mAction.value= TransactionAction(TransactionAction.CLICK_CASH_DEPOSIT)
    }

    fun clickCashWithdrawal(view:View)
    {
        mAction.value= TransactionAction(TransactionAction.CLICK_CASH_WITHDRAWAL)
    }

    fun clickTransfer(view:View)
    {
        mAction.value= TransactionAction(TransactionAction.CLICK_TRANSFER)
    }

    fun clickTNeft(view:View)
    {
        mAction.value= TransactionAction(TransactionAction.CLICK_NEFT)
    }

    fun clickLoanClosing(view:View)
    {
        mAction.value= TransactionAction(TransactionAction.CLICK_LOAN_CLOSING)
    }

    fun clickLoanCollection(view:View)
    {
        mAction.value= TransactionAction(TransactionAction.CLICK_LOAN_COLLECTION)
    }

    override fun onCleared() {
        super.onCleared()
        mAction.value= TransactionAction(TransactionAction.DEFAULT)
    }
}