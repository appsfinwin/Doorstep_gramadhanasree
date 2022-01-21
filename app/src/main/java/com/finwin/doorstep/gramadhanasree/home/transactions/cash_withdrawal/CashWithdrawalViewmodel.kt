package com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal

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
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.action.CashWithdrawalAction
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.databinding.FragmentCashWithdrawalBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class CashWithdrawalViewmodel(application: Application) : AndroidViewModel(application) {

    var sharedPreferences: SharedPreferences = application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var repository: CashWithdrawalRepository = CashWithdrawalRepository()
    var mAction: MutableLiveData<CashWithdrawalAction> = MutableLiveData()
    var accountNumber: ObservableField<String> = ObservableField("")
    var apiInterface: ApiInterface? = RetrofitClient().getApi()
    var name: ObservableField<String> = ObservableField("")
    var otpId: ObservableField<String> = ObservableField("")
    var otp: ObservableField<String> = ObservableField("")
    var acountNUmber: ObservableField<String> = ObservableField("")
    var accountStatus: ObservableField<String> = ObservableField("")
    var mobile: ObservableField<String> = ObservableField("")
    var amount: ObservableField<String> = ObservableField("")
    var visibility: ObservableField<Int> = ObservableField(View.GONE)
    var otpVisibility: ObservableField<Int> = ObservableField(View.GONE)
    lateinit var binding: FragmentCashWithdrawalBinding
    var loading: SweetAlertDialog? = null
    lateinit var account: Account
    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    init {
        otpVisibility.set(View.GONE)
        repository.mAction = mAction
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }

    fun clickSearch(view: View) {
        visibility.set(View.GONE)
        mAction.value =
            CashWithdrawalAction(
                CashWithdrawalAction.CLICK_SEARCH
            )
    }

    fun clickSubmit(view: View) {
        if (accountNumber.get().equals("")) {
            Services.showSnakbar("Account number cannot be empty", view)
        } else {
            initLoading(view.context)
            getAccountHolder()
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


    private fun generateOtp() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] = accountNumber.get()
        jsonParams["particular"] = "Withdrawal"
        jsonParams["amount"] = amount.get()
        jsonParams["agent_id"] = sharedPreferences.getString(Constants.AGENT_ID, "")

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        apiInterface?.let { repository.generateOtp(it, body) }
    }

    private fun cashWithdrawal() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] = accountNumber.get()
        jsonParams["particular"] = "Withdrawal"
        jsonParams["withdrawal_amount"] = amount.get()
        jsonParams["auth_mode"] = amount.get()
        jsonParams["otp_val"] = otp.get()
        jsonParams["otp_id"] = otpId.get()
        jsonParams["agent_id"] = sharedPreferences.getString(Constants.AGENT_ID, "")

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface?.let { repository.cashWithdrawal(it, body) }
    }


    fun reset() {
        accountNumber.set("")
        visibility.set(View.GONE)
        otpVisibility.set(View.GONE)
    }

    fun clickSubmitAmount(view: View) {
        if (amount.get().equals("")) {
            Services.showSnakbar("Please enter amount", view)
        } else {
            initLoading(view.context)
            otpVisibility.set(View.VISIBLE)
            binding.etOtpLayout.editText?.requestFocus()
            binding.etOtpLayout.requestFocus()
            generateOtp()
        }


    }

    fun clickSubmitOtp(view: View) {
        if (otp.get().equals(""))
        {
            Services.showSnakbar("Please enter OTP",view)
        }else{
            mAction.value= CashWithdrawalAction(CashWithdrawalAction.CLICK_CASH_WITHDRAWAL)
        }

    }

    fun setAccountHolderData(account: Account) {
        otpVisibility.set(View.GONE)
        this.account = account
        visibility.set(View.VISIBLE)
        name.set(account.data.NAME)
        accountNumber.set(account.data.ACNO)
        accountStatus.set(account.data.ACC_STATUS)
        mobile.set(account.data.MOBILE)

    }

    fun set(binding: FragmentCashWithdrawalBinding?) {
        if (binding != null) {
            this.binding = binding
        }

    }

    fun clickCashWithDrawal(it: View?) {
        if (it != null) {
            initLoading(it.context)
        }
        cashWithdrawal()
    }


}