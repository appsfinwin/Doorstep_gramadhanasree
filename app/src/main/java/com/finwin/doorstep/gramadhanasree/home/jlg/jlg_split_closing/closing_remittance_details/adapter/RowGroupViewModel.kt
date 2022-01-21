package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_remittance_details.adapter

import androidx.databinding.BaseObservable
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Dat

class RowGroupViewModel (
    var dataItem: List<Dat>,
    var dataListLiveData: MutableLiveData<List<Dat>>,
    var position: Int
) : BaseObservable() {

    var customerId: String=dataItem[position].CustomerID
    var customerName: String=dataItem[position].CustomerName
    var accountNumber: String=dataItem[position].accountNumber
    var interest: String=dataItem[position].Interest
    var principalBalance: String=dataItem[position].PrincipalBalance
    var penalInterest: String=dataItem[position].PenalInterest
    var principalDue: String=dataItem[position].PrincipalDue
    var totalInterest: String=dataItem[position].TotalInterest


    val isChecked = ObservableField(getIsChecked(dataItem[position].IsClosing))
    val remitance : ObservableField<String> = ObservableField(dataItem[position].Remittance)

    fun getIsChecked(value: String): Boolean{
        var check=false
        check = !value.equals("F")
        return check
    }
    fun onRemittanceAmountChanged(text: CharSequence?) {

        (if (text.toString() == "") "0" else text.toString())

        dataItem[position].Remittance=  (if (text.toString() == "") "0" else text.toString())
        dataListLiveData.value= dataItem
//        if (isPhoneVerified.get() == true) {
//            verifyPhoneNumber.setValue(false)
//        }


    }
    fun onTypeChecked(checked: Boolean, i: Int) {
//        if (checked) {
//
//           // mAction.value= JlgAction(JlgAction.SELECT_ACCOUNT,dataItem[position])
//
//            var totalAmount: Double =
//               interest.toDouble()+principalBalance.toDouble() + penalInterest.toDouble() + totalInterest.toDouble()+ principalDue.toDouble()
//            remitance.set(totalInterest.toString())
//
//        } else {
//            mAction.value= JlgAction(JlgAction.DE_SELECT_ACCOUNT,dataItem[position])
//            remitance.set(dataItem[position].Remittance)
//
//        }


        if (checked) {
            dataItem[position].IsClosing="T"
            dataListLiveData.value=dataItem
        } else {
            dataItem[position].IsClosing="F"
            dataListLiveData.value=dataItem
        }
    }

}