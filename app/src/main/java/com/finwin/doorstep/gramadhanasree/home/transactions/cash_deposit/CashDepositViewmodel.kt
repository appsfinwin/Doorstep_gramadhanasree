package com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo.Account
import com.finwin.doorstep.gramadhanasree.utils.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*


class CashDepositViewmodel(application: Application) : AndroidViewModel(application) {

    var sharedPreferences: SharedPreferences =
        application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var repository: CashDepositRepository = CashDepositRepository()
    var mAction: MutableLiveData<CashDepositAction> = MutableLiveData()
    var accountNumber: ObservableField<String> = ObservableField("")
    var apiInterface: ApiInterface ? = RetrofitClient().getApi()
    var name: ObservableField<String> = ObservableField("")
    var acountNUmber: ObservableField<String> = ObservableField("")
    var accountStatus: ObservableField<String> = ObservableField("")
    var mobile: ObservableField<String> = ObservableField("")
    var amount: ObservableField<String> = ObservableField("")
    var visibility: ObservableField<Int> = ObservableField(View.GONE)
    var loading: SweetAlertDialog? = null
    var BILL = ""
    lateinit var account: Account
    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    init {
        repository.mAction = mAction
    }

    fun clickSearch(view: View) {
        mAction.value = CashDepositAction(CashDepositAction.DEFAULT)
        visibility.set(View.GONE)

        mAction.value =
            CashDepositAction(
                CashDepositAction.CLICK_SEARCH
            )
    }

    fun clickSubmit(view: View) {
        if (accountNumber.get().equals("")) {
            Services.showSnakbar("Account number cannot be empty", view)
        } else {
            amount.set("")
            initLoading(view.context)
            getAccountHolder()
        }

    }

    fun clickDeposit(view: View) {
        mAction.value = CashDepositAction(CashDepositAction.DEFAULT)
        if (amount.get().equals("")) {
            Services.showSnakbar("Amount cannot be empty", view)
        } else {
            //print()

            mAction.value = CashDepositAction(CashDepositAction.CLICK_DEPOSIT)
        }
    }

    private fun getAccountHolder() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] = accountNumber.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        apiInterface?.let { repository.getAccountHolder(it, body) }
    }


    private fun cashDeposit() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] = accountNumber.get()
        jsonParams["deposit_amount"] = amount.get()
        jsonParams["agent_id"] = sharedPreferences.getString(Constants.AGENT_ID, "")
        jsonParams["particular"] = "FromApp"

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        var request: String = JSONObject(jsonParams).toString()
        request = JSONObject(jsonParams).toString()

        apiInterface?.let { repository.cashDeposit(it, body) }
    }

    fun setAccountHolderData(account: Account) {
        this.account = account
        visibility.set(View.VISIBLE)
        name.set(account.data.NAME)
        accountNumber.set(account.data.ACNO)
        accountStatus.set(account.data.ACC_STATUS)
        mobile.set(account.data.MOBILE)
    }


    override fun onCleared() {
        super.onCleared()
        reset()

    }

    fun clickCashDeposit(it: View?) {

        if (it != null) {
            initLoading(it.context)
            cashDeposit()
        }
    }

    fun reset() {
        accountNumber.set("")
        visibility.set(View.GONE)
    }

}