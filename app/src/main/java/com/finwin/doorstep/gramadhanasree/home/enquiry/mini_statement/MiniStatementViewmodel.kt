package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement

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
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.action.MiniStatementAction
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.pojo.Account
import com.finwin.doorstep.gramadhanasree.utils.Constants
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap

class MiniStatementViewmodel(application: Application) : AndroidViewModel(application) {
    var sharedPreferences: SharedPreferences= application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()
    var repository: MiniStatementRepository = MiniStatementRepository()
    var mAction: MutableLiveData<MiniStatementAction> = MutableLiveData()
    var accountNumber: ObservableField<String> = ObservableField("")
    var apiInterface: ApiInterface? = RetrofitClient().getApi()
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
        repository.mAction=mAction
    }

    fun clickSearch(view: View)
    {
        visibility.set(View.GONE)
        mAction.value=
            MiniStatementAction(
                MiniStatementAction.CLICK_SEARCH
            )
    }

    fun clickSubmit(view: View)
    {
        if(accountNumber.get().equals(""))
        {
            Services.showSnakbar("Account number cannot be empty",view)
        }else {
            initLoading(view.context)
            getAccountHolder()
        }

    }

    fun clickSowMiniStatement(view: View)
    {

        mAction.value = MiniStatementAction(MiniStatementAction.CLICK_MINI_STATEMENT)

    }

    private fun getAccountHolder() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] =accountNumber.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface?.let { repository.getAccountHolder(it, body) }
    }

    fun setAccountHolderData(account: Account) {
        this.account=account
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

    fun reset()
    {
        accountNumber.set("")
        visibility.set(View.GONE)
    }
}