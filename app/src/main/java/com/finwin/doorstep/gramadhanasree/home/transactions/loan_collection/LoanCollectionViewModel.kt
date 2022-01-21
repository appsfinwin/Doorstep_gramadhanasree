package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo.GetLoanAccountHolderResponse
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.pojo.Receiptdetail

import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class LoanCollectionViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    var accountNumber: ObservableField<String> = ObservableField("")
    var customerName: ObservableField<String> = ObservableField("")
    var schemeCode: ObservableField<String> = ObservableField("")

    var apiInterface= RetrofitClient().getApi()
    var custId=ObservableField("")
    var loanDate=ObservableField("")
    var interest=ObservableField("")
    var loanAmount=ObservableField("")
    var loanNumber=ObservableField("")
    var loanType=ObservableField("")
    var period=ObservableField("")
    var remittanceAmount=ObservableField("")
    var totalBalance=ObservableField("")
    var detailsVisibility= ObservableField(View.GONE)


    var sharedPreferences : SharedPreferences =
        application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)

    private var _mAction: MutableLiveData<LoanCollectionAction> = MutableLiveData()
    var mAction: LiveData<LoanCollectionAction> =_mAction
    init {
        LoanCollectionRepository.mAction= _mAction
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

    fun onAccountNumberChanged(text: CharSequence?) {
        reset()
    }


    fun getLoanAccountHolder() {
        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] = accountNumber.get()
        jsonParams["demandDate"] = ""
        jsonParams["flag"] = ""
        jsonParams["branch_id"] = sharedPreferences.getString(Constants.BRANCH_ID, "")
        // jsonParams["sch_code"] = schemeCode.get()
        jsonParams["sch_code"] = ""

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        var request=  JSONObject(jsonParams).toString()
        request=  JSONObject(jsonParams).toString()

        apiInterface?.let { LoanCollectionRepository.getLoanAccountHolder(apiInterface = it, body = body) }
    }



    fun clickSubmit(view: View)
    {
        if(accountNumber.get().equals(""))
        {
            Toast.makeText(view.context, "Account number cannot be empty", Toast.LENGTH_SHORT).show()
        }else{
            initLoading(view.context)
            getLoanAccountHolder()
        }
    }

    fun reset() {

        accountNumber.set("")
        customerName.set("")
        custId.set("")
        loanDate.set("")
        interest.set("")
        loanAmount.set("")
        loanNumber.set("")
        loanType.set("")
        period.set("")
        remittanceAmount.set("")
        totalBalance.set("")
        detailsVisibility.set(View.GONE)

    }

    fun setCustomerDetails(response: GetLoanAccountHolderResponse) {

        customerName.set(response.name)
        custId.set(response.customerNumber)
        loanDate.set(response.loanDate)
        interest.set(response.interestRate)
        loanAmount.set(response.loanAmount)
        loanNumber.set(response.loanNumber)
        loanType.set(response.loanType)
        period.set(response.loanPeriod)

        getLoanAmount(response.receiptDetails)

    }

    private fun getLoanAmount(receiptDetails: List<Receiptdetail>) {

        try {
            var amount = 0.0

            for (i in receiptDetails.indices) {
                amount += (if (receiptDetails[i].balance == "") "0" else receiptDetails[i].balance).toDouble()
            }
            totalBalance.set(amount.toString())
        }catch (e:Exception)
        {
            e.printStackTrace()
        }


    }


    fun clickLoanCollection(view: View)
    {
        if (remittanceAmount.get().equals(""))
        {
            Toast.makeText(view.context, "Please enter amount!", Toast.LENGTH_SHORT).show()
        }
        else if (remittanceAmount.get()?.toDouble()!! >= totalBalance.get()?.toDouble()!!)
        {
            Toast.makeText(view.context, "Enter an amount less than total balance", Toast.LENGTH_SHORT).show()
        }else
        {
            _mAction.value= LoanCollectionAction(LoanCollectionAction.CLICK_LOAN_COLLECTION)
        }
    }

    fun loanCollection(view: View?) {
        initLoading(view?.context)

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] = accountNumber.get()
        jsonParams["deposit_amount"] = remittanceAmount.get()
        jsonParams["Agent_Id"] = sharedPreferences.getString(Constants.AGENT_ID, "")

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        var request=  JSONObject(jsonParams).toString()
        request=  JSONObject(jsonParams).toString()

        apiInterface?.let { LoanCollectionRepository.loanCollection(apiInterface = it, body = body) }
    }
}