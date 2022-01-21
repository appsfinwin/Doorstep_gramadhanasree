package com.finwin.doorstep.gramadhanasree.home.enquiry.balance_enquiry

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.enquiry.balance_enquiry.action.BalanceAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class BalanceEnquiryViewModel : ViewModel() {


    var repository: BalanceRnquiryRepository = BalanceRnquiryRepository()
    var mAction:MutableLiveData<BalanceAction> = MutableLiveData()
    var accountNumber: ObservableField<String> = ObservableField("")
    var mainLayoutVisibility: ObservableField<Int> = ObservableField(View.VISIBLE)
    var receiptVisibility: ObservableField<Int> = ObservableField(View.GONE)

    var dateAndTime:ObservableField<String> =ObservableField("")
    var name:ObservableField<String> =ObservableField("")
    var mobile:ObservableField<String> =ObservableField("")
    var currentBalance:ObservableField<String> = ObservableField("")
    var BILL = ""
    var apiInterface: ApiInterface? = RetrofitClient().getApi()
    init {
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

    public fun balanceEnquiry() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["account_no"] = accountNumber.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        apiInterface?.let { repository.balanceEnquiry(it, body) }
    }

    fun clickSubmit(view: View)
    {
        if(accountNumber.get().equals(""))
        {
            Services. showSnakbar("Account number cannot be empty",view)
        }else {
            initLoading(view.context)
            balanceEnquiry()
        }

    }
    fun clickSearch(view:View)
    {
        //visibility.set(View.GONE)
        mAction.value=
            BalanceAction(
                BalanceAction.CLICK_SEARCH
            )
    }




    override fun onCleared() {
        super.onCleared()

    }

}