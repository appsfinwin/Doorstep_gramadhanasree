package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Charges
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Dat
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class SplitTransactionViewmodel(application: Application) : AndroidViewModel(application) {

    var accountsData: ObservableArrayList<Dat> = ObservableArrayList()
    var chargesList: ObservableArrayList<Charges> = ObservableArrayList()


    var sharedPreferences: SharedPreferences = application.getSharedPreferences(
        com.finwin.doorstep.gramadhanasree.utils.Constants.PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )
    var mAction: MutableLiveData<JlgAction> = MutableLiveData()
    var accountsLiveData: MutableLiveData<List<Dat>> = MutableLiveData()
    var subTranType = ObservableField("")
    var groupAccountNumber = ObservableField("")
    var schemeCode = ObservableField("")

    var transactionDate = ObservableField("")
    var effectiveDate = ObservableField("")
    var tranType = ObservableField("")
    var transferAccountNumber = ObservableField("")
    var narration = ObservableField("")
    var instrumentNumber = ObservableField("")
    var instrumentDate = ObservableField("")
    var instrumentType = ObservableField("")


//    var agentId = ObservableField("")
//    var branchCode = ObservableField("")

    var totalAmount = ObservableField("")
    var particulars = ObservableField("")

    var apiInterface = RetrofitClient().getApi()!!
    lateinit var repository: SplitTransactionRepository

    init {
        repository = SplitTransactionRepository().getInstance()
        repository.mAction = mAction

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

    public fun getCodeMasters() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        repository.getCodeMasters(apiInterface)

    }


    public fun groupAccountDetails(groupAccountNumber: String, subTransTYpe: String) {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["Accno"] = groupAccountNumber
        jsonParams["SubTranType"] = subTransTYpe

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        repository.groupAccountDetails(apiInterface, body)
    }

    fun setAccountData(data: List<Dat>) {
        try {
            var amount = 0.0
            accountsData.clear()
            for (i in data.indices) {
                accountsData.add(data[i])
                amount += (if (data[i].Remittance == "") "0" else data[i].Remittance).toDouble()
            }
            totalAmount.set(amount.toString())
        }catch (e:Exception)
        {
            e.printStackTrace()
        }
    }

    fun setChargesData(it: List<Charges>?) {
        chargesList.clear()
        if (it != null) {
            for (i in it.indices) {
                chargesList.add(it[i])
            }
        }
    }


    fun RemittanceData(): String? {
        var StrJson = ""
        var obj: JSONObject
        val jsonArray = JSONArray()
        try {
            // if (com.finwingway.digicob.loanModule.JLGwithSplit.TransactionLoanAdapterThree.TrType == "SPLIT") {
            if (accountsData.size > 0) {
                for (i in 0 until accountsData.size) {
                    obj = JSONObject()
                    obj.put("CustId", accountsData[i].CustomerID)
                    obj.put("CustName", accountsData[i].CustomerName)
                    obj.put("AccountNo", accountsData[i].accountNumber)
                    obj.put("PrincipalBlnc", accountsData[i].PrincipalBalance)
                    obj.put("PrincipalDue", accountsData[i].PrincipalDue)
                    obj.put("Interest", accountsData[i].Interest)
                    obj.put("PenalInterest", accountsData[i].PenalInterest)
                    obj.put("TotalInterest", accountsData[i].TotalInterest)
                    obj.put("Remittance", accountsData[i].Remittance)
                    //obj.put("Closing", TransactionLoanRecyclerSplit.dataSet.get(i).getSplitSelected());
                    obj.put("Closing", "F")
                    jsonArray.put(obj)
                }
                StrJson = jsonArray.toString()
                //   }
            } else {
                if (accountsData.size > 0) {
                    for (i in 0 until (accountsData.size)) {
                        obj = JSONObject()
                        obj.put("CustId", accountsData[i].CustomerID)
                        obj.put("CustName", accountsData[i].CustomerName)
                        obj.put("Principal", accountsData[i].PrincipalBalance)
                        obj.put("Interest", accountsData[i].Interest)
                        obj.put("PenalInterest", accountsData[i].PenalInterest)
                        obj.put("TotalInterest", accountsData[i].TotalInterest)
                        obj.put("Remittance", accountsData[i].Remittance)
                        //obj.put("Closing", TransactionLoanRecycler.dataSet.get(i).getSelected());
                        obj.put("Closing", "F")
                        jsonArray.put(obj)
                    }
                    StrJson = jsonArray.toString()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return StrJson
    }


    fun ChargesData(): String? {
        var StrJson = ""
        var obj: JSONObject
        val jsonArray = JSONArray()
        try {
            //   if (com.finwingway.digicob.loanModule.JLGwithSplit.TransactionLoanAdapterThree.TrType == "SPLIT") {
            if (chargesList.size > 0) {
                for (i in 0 until chargesList.size) {
                    obj = JSONObject()
                    obj.put("AccNo", chargesList[i].accountNumber)
                    obj.put("BankChargeCode", chargesList[i].chargeId)
                    obj.put("Charge", chargesList[i].charges)
                    obj.put("Amount", chargesList[i].amount)
                    jsonArray.put(obj)
                }
                StrJson = jsonArray.toString()
            }
            // }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return StrJson
    }

    fun splitTransaction() {

        var groupData = RemittanceData()
        var chargesData = ChargesData()
        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["Ln_Type"] = "SPLIT"
        jsonParams["Brcode"] =
            sharedPreferences.getString(com.finwin.doorstep.gramadhanasree.utils.Constants.BRANCH_ID, "")
        jsonParams["Tran_no"] = ""
        jsonParams["Schcode"] = schemeCode.get()
        jsonParams["Accno"] = groupAccountNumber.get()
        jsonParams["Trdate"] = transactionDate.get()
        jsonParams["Transacteddate"] = transactionDate.get()
        jsonParams["Effectivedate"] = effectiveDate.get()
        jsonParams["TranType"] = tranType.get()
        jsonParams["SubTranType"] = subTranType.get()
        jsonParams["Amount"] = totalAmount.get()
        jsonParams["TransferAccNo"] = transferAccountNumber.get()
        jsonParams["Narration"] = particulars.get()
        jsonParams["InstrumentNo"] = instrumentNumber.get()
        jsonParams["InstrumentDate"] = instrumentDate.get()
        jsonParams["InstrumentType"] = instrumentType.get()
        jsonParams["MakerId"] =
            sharedPreferences.getString(com.finwin.doorstep.gramadhanasree.utils.Constants.AGENT_ID, "")
        jsonParams["Makingtime"] = ""
        jsonParams["CheckerId"] = ""
        jsonParams["Checkingtime"] = ""
        jsonParams["flag"] = "INSERT"
        jsonParams["IsClosing"] = "Y"
        jsonParams["table"] = ""
        jsonParams["AccountDetails"] = groupData
        jsonParams["Charge"] = chargesData


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        var request = JSONObject(jsonParams).toString()
        request = JSONObject(jsonParams).toString()

        repository.splitTransaction(apiInterface, body)

    }

    fun callSpitTransactionApi(context: Context?) {

        if (subTranType.get().equals("")) {
            Toast.makeText(context, "sub transaction type is empty", Toast.LENGTH_SHORT).show()
        } else if (groupAccountNumber.get().equals("")) {
            Toast.makeText(context, "groupAccountNumber is empty", Toast.LENGTH_SHORT).show()
        } else if (schemeCode.get().equals("")) {
            Toast.makeText(context, "schemeCode is empty", Toast.LENGTH_SHORT).show()
        } else if (transactionDate.get().equals("")) {
            Toast.makeText(context, "transactionDate is empty", Toast.LENGTH_SHORT).show()
        } else if (effectiveDate.get().equals("")) {
            Toast.makeText(context, "effectiveDate is empty", Toast.LENGTH_SHORT).show()
        } else if (tranType.get().equals("")) {
            Toast.makeText(context, "tranType is empty", Toast.LENGTH_SHORT).show()
        } else if (tranType.get().equals("T") && transferAccountNumber.get().equals("")) {
            Toast.makeText(context, " transfer Account number cannot be empty", Toast.LENGTH_SHORT)
                .show()
        } else if (instrumentNumber.get().equals("")) {
            Toast.makeText(context, "instrumentNumber is empty", Toast.LENGTH_SHORT).show()
        } else if (instrumentDate.get().equals("")) {
            Toast.makeText(context, "instrumentDate is empty", Toast.LENGTH_SHORT).show()
        } else if (instrumentType.get().equals("")) {
            Toast.makeText(context, "instrumentType is empty", Toast.LENGTH_SHORT).show()
        } else if (totalAmount.get().equals("")) {
            Toast.makeText(context, "totalAmount is empty", Toast.LENGTH_SHORT).show()
        } else if (particulars.get().equals("")) {
            Toast.makeText(context, "particulars is empty", Toast.LENGTH_SHORT).show()
        } else {
            initLoading(context)
            splitTransaction()
        }

    }

    fun clearData() {

        subTranType.set("")
        groupAccountNumber.set("")
        schemeCode.set("")
        transactionDate.set("")
        effectiveDate.set("")
        tranType.set("")
        transferAccountNumber.set("")
        narration.set("")
        instrumentNumber.set("")
        instrumentDate.set("")
        instrumentType.set("")
        totalAmount.set("")
        particulars.set("")

    }

}