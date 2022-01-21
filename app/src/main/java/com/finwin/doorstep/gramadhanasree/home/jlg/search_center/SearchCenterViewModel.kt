package com.finwin.doorstep.gramadhanasree.home.jlg.search_center

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.LoanCollectionRepository
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap

class SearchCenterViewModel(application: Application) : AndroidViewModel(application) {

    var apiInterface = RetrofitClient().getApi()!!
    var rbCenterCode = ObservableField(true)
    var rbCenterName = ObservableField(false)
    var etSearch = ObservableField("")
    var centerName = ObservableField("")
    var centerCode = ObservableField("")
    var etSearchHint = ObservableField("")
    var mRadioButton = MutableLiveData("")
    var sharedPreferences : SharedPreferences =
        application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
     var mAction: MutableLiveData<SearchCenterAction> = MutableLiveData()
    init {
        SearchCenterRepository.mAction= mAction
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

    fun clickSearch(view: View)
    {
        if(rbCenterCode.get() == true){
            centerCode.set(etSearch.get())
        }else{
            centerName.set(etSearch.get())
        }
        initLoading(view.context)
        getSearchGroup()
    }

    fun clickCancel(view: View)
    {

    }

    fun radioChanged(radioGroup: RadioGroup, id: Int) {
        val radioButton = radioGroup.findViewById<View>(id) as RadioButton
        val radio = radioButton.text.toString()
        when (radio) {
            "Center Name" -> {
                etSearch.set("")
                centerCode.set("")
                centerName.set(etSearch.get())
                etSearchHint.set("Enter Center Name")
                mRadioButton.value="Center Name"
                rbCenterCode.set(false)
                rbCenterName.set(true)
            }
            "Center Code" -> {
                etSearch.set("")
                centerName.set("")
                centerCode.set(etSearch.get())
                mRadioButton.value="Center Code"
                etSearchHint.set("Enter Center Code")
                rbCenterCode.set(true)
                rbCenterName.set(false)
            }
        }
    }

    fun getSearchGroup() {
        val jsonParams: MutableMap<String?, Any?> = HashMap()

        jsonParams["Brcode"] = sharedPreferences.getString(Constants.BRANCH_ID, "")
        jsonParams["CentreCode"] = centerCode.get()
        jsonParams["CentreName"] = centerName.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        SearchCenterRepository.getSearchCenter(apiInterface = apiInterface, body = body)
    }

}