package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.reciept

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.action.MiniStatementAction
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.pojo.MiniStatementData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services

import com.softland.palmtecandro.palmtecandro
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class MiniStatementReciepViewmodel : ViewModel() {

    var mAction: MutableLiveData<MiniStatementAction> = MutableLiveData()
    var repository: MiniStatementRecieptRepository = MiniStatementRecieptRepository()
    var accountNumber: ObservableField<String> = ObservableField("")
    var apiInterface: ApiInterface? = RetrofitClient().getApi()
     lateinit var miniStatementData: MiniStatementData
    var BILL = ""

    init {
        repository.mAction = mAction

        if (android.os.Build.MODEL.contains("PALMTEC")) {
            palmtecandro.jnidevOpen(115200)
        }

    }

    var loading: SweetAlertDialog? = null

    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    public fun miniStatement() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] = accountNumber.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface?.let { repository.getAccountHolder(it, body) }
    }

    override fun onCleared() {
        super.onCleared()
        mAction.value= MiniStatementAction(MiniStatementAction.DEFAULT)
        if (android.os.Build.MODEL.contains("PALMTEC")) {
            palmtecandro.jnidevClose()
        }
    }

    fun print() {
        try {
//                ptr.iFlushBuf();


//                    BILL = "                Bank Name               \n\n";
                BILL = "                CHOSEN VOLKS NIDHI LTD               \n\n";
                BILL = BILL + " Account Number  : " + miniStatementData.ACC_NO + "\n";
                BILL = BILL + " Account Name    : " + miniStatementData.NAME  + "\n";
                BILL = BILL + " Mobile Number   : " + miniStatementData.MOBILE  + "\n";
                BILL = BILL + " Current Balance : " + miniStatementData.CURRENT_BALANCE  + "\n\n";
//                    BILL = BILL + "   DATE    TYPE Dr/Cr AMT  BAL" + "\n\n";
                BILL = BILL + "   DATE    Dr/Cr  AMT  BAL" + "\n\n";


                for (i in miniStatementData.TRANSACTONS.indices) {

                    BILL = BILL +  miniStatementData.TRANSACTONS[i].TXN_DATE + " " +  miniStatementData.TRANSACTONS[i].TRAN_TYPE + "   " +  miniStatementData.TRANSACTONS[i].DORC + "  " +  miniStatementData.TRANSACTONS[i].TXN_AMOUNT + "  " +  miniStatementData.TRANSACTONS[i].BALANCE + "\n";
                    /* BILL = BILL + "Transaction ID     : " + ptxnid + "\n";
                     BILL = BILL + "Particular         : " + pParticular + "\n";
                     BILL = BILL + "Debit or Credit    : " + pDebitorCre + "\n";
                     BILL = BILL + "Transaction Amount : " + pamount + "\n";
                     BILL = BILL + "Account Balance    : " + pbalance + "\n";
                     BILL = BILL
                             + "------------------------------";*/
                }
                BILL = BILL + "\n\n";
                BILL = BILL + "Thank you for Banking with us...\n"
//                    BILL = BILL + "      Keep Smiling :)\n";
                BILL = BILL+ "------------------------------\n\n\n";

            Print(BILL)



        } catch (e:NullPointerException) {
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

    fun setData(data: MiniStatementData) {

        this.miniStatementData=data

    }

    fun clickPrint(view:View)
    {
//        mAction.value="print"

        if (android.os.Build.MODEL.contains("PALMTEC")) {
            print()
        }else
        {
            Toast.makeText(view.context,"Printing not supported in this device", Toast.LENGTH_LONG).show()
        }
    }


}