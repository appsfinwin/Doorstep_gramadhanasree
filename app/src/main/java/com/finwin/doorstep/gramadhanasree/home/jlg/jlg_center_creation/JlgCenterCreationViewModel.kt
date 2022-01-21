package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog

import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.JlgCentercreationRepository
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap

class JlgCenterCreationViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel

    var sharedPreferences: SharedPreferences = application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()
    var repository : JlgCentercreationRepository = JlgCentercreationRepository().getInstance()
    var mAction: MutableLiveData<JlgAction> = MutableLiveData()
    var apiInterface: ApiInterface
    var centerName: ObservableField<String> =ObservableField("")
    var centerCode: ObservableField<String> = ObservableField("")

    init {
        apiInterface = RetrofitClient().getApi()!!
        repository.mAction=mAction

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
    public fun getJlgCenter() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()

        jsonParams["Brcode"] = sharedPreferences.getString(Constants.BRANCH_ID, "")

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        repository.getJlgCenter(apiInterface, body)
    }

    public fun createJlgCenter() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()

        jsonParams["Brcode"] = sharedPreferences.getString(Constants.BRANCH_ID, "")
        jsonParams["MakerId"] = sharedPreferences.getString(Constants.AGENT_ID, "")
        jsonParams["CentreCode"] =centerCode.get()
        jsonParams["CentreName"] =centerName.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        repository.jlgCreateCenter(apiInterface, body)
    }

    public fun clickSubmit(view: View)
    {
        if (centerCode.get().equals(""))
        {
         Services.showSnakbar("Center code cannot be empty",view)
        }else if (centerName.get().equals(""))
        {
            Services.showSnakbar("Center name cannot be empty",view)
        }else
        {
            initLoading(view.context)
            createJlgCenter()
        }
    }

    fun resetText() {
        centerName.set("")
        centerCode.set("")
    }


}