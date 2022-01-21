package com.finwin.doorstep.gramadhanasree.print_reciept.loan_recipt

import android.app.Application
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel

import com.finwin.doorstep.gramadhanasree.R
import com.softland.palmtecandro.palmtecandro

class LoanReceiptViewModel(application: Application) : AndroidViewModel(application) {

    var transactionDate: ObservableField<String> = ObservableField("")
    var transactionTime: ObservableField<String> = ObservableField("")
    var loanAccountNumber: ObservableField<String> = ObservableField("")
    var name: ObservableField<String> = ObservableField("")
    var remittanceAmount: ObservableField<String> = ObservableField("")
    var bankName: ObservableField<String> = ObservableField("")

    var BILL_CASH = ""


    fun setReceipt(
        transactionDate: String,
        transactionTime: String,
        loanAccountNumber: String,
        name: String,
        remittanceAmount: String
    ) {
        this.transactionDate.set(transactionDate)
        this.transactionTime.set(transactionTime)
        this.loanAccountNumber.set(loanAccountNumber)
        this.name.set(name)
        this.remittanceAmount.set(remittanceAmount)
    }

    fun clickPrint(view: View) {
//        mAction.value="print"

        if (android.os.Build.MODEL.contains("PALMTEC")) {

            printPalmtech()

        } else if (android.os.Build.MODEL.contains("V2")) {

            printV2()


        } else {
            Toast.makeText(view.context, "Printing not supported in this device", Toast.LENGTH_LONG)
                .show()
        }
    }

    fun printPalmtech() {
        try {

            BILL_CASH = "\n       " + R.string.bank_name + "          \n\n";
            BILL_CASH = BILL_CASH + "Date             : " + transactionDate.get() + "\n";
            BILL_CASH = BILL_CASH + "Time          : " + transactionTime.get() + "\n";
            BILL_CASH = BILL_CASH + "Loan Account Number   : " + loanAccountNumber.get() + "\n";
            BILL_CASH = BILL_CASH + "Name             : " + name.get() + "\n";
//            BILL_CASH = BILL_CASH + "Mobile Number    : " + mobile.get() + "\n";
            BILL_CASH = BILL_CASH + "------------------------------";
            BILL_CASH = BILL_CASH + "\n\n";
            BILL_CASH = BILL_CASH + "Remittance Amount  : " + remittanceAmount.get() + "\n";
//            BILL_CASH = BILL_CASH + "Deposit Amount   : " + depositAmount.get() + "\n";
//            BILL_CASH = BILL_CASH + "Current Balance  : " + currentBalance.get() + "\n\n";
            BILL_CASH = BILL_CASH + "Thank you for Banking with us...\n";
            BILL_CASH = BILL_CASH + "------------------------------\n\n\n";

            Print(BILL_CASH)

        } catch (e: NullPointerException) {
        }
    }

    fun printV2() {
        try {

            BILL_CASH = "\n       " + R.string.bank_name + "          \n\n";
            BILL_CASH = BILL_CASH + "Date             : " + transactionDate.get() + "\n";
            BILL_CASH = BILL_CASH + "Time          : " + transactionTime.get() + "\n";
            BILL_CASH = BILL_CASH + "Loan Account Number   : " + loanAccountNumber.get() + "\n";
            BILL_CASH = BILL_CASH + "Name             : " + name.get() + "\n";
//            BILL_CASH = BILL_CASH + "Mobile Number    : " + mobile.get() + "\n";
            BILL_CASH = BILL_CASH + "------------------------------";
            BILL_CASH = BILL_CASH + "\n\n";
            BILL_CASH = BILL_CASH + "Remittance Amount  : " + remittanceAmount.get() + "\n";
//            BILL_CASH = BILL_CASH + "Deposit Amount   : " + depositAmount.get() + "\n";
//            BILL_CASH = BILL_CASH + "Current Balance  : " + currentBalance.get() + "\n\n";
            BILL_CASH = BILL_CASH + "Thank you for Banking with us...\n";
            BILL_CASH = BILL_CASH + "------------------------------\n\n\n";



            //SunmiPrintHelper.getInstance().printText(BILL_CASH, 18F, false, false)

        } catch (e: NullPointerException) {
        }
    }


    private fun Print(input: String) {
        var iLen = 0
        var iCount = 0
        var iPos = 0
        val _data = input.toByteArray()
        iLen = _data.size
        iLen += 4
        val dataArr = IntArray(iLen)
        dataArr[0] = 0x1B.toByte().toInt()
        dataArr[1] = 0x21.toByte().toInt()
        dataArr[2] = 0x00.toByte().toInt()
        iCount = 3
        while (iCount < iLen - 1) {
            dataArr[iCount] = _data[iPos].toInt()
            iCount++
            iPos++
        }
        dataArr[iCount] = 0x0A.toByte().toInt()
        palmtecandro.jnidevDataWrite(dataArr, iLen)
    }




}