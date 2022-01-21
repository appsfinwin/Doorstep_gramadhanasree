package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists
import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap

class JlgPendingListViewModel(application: Application) : AndroidViewModel(application) {

    var mAction: MutableLiveData<JlgAction> = MutableLiveData()
    var apiInterface = RetrofitClient().getApi()!!
    val sharedPreferences: SharedPreferences =application.getSharedPreferences(
        Constants.PREFERENCE_NAME,
        Context.MODE_PRIVATE
    )
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
    init {
        PendingListRepository.mAction= mAction
    }
    fun getPendingList() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["BranchCode"] = sharedPreferences.getString(Constants.BRANCH_ID,"")
        //jsonParams["BranchCode"] = "01"
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        var request= JSONObject(jsonParams).toString()
        request= JSONObject(jsonParams).toString()
        PendingListRepository.getPendingList(apiInterface, body)

    }


    fun removeAccount(accountNumber:String) {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        //jsonParams["BranchCode"] = sharedPreferences.getString(Constants.AGENT_ID,"")
        jsonParams["BranchCode"] = "01"
        jsonParams["Acc_No"] = accountNumber
        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        var request= JSONObject(jsonParams).toString()
        request= JSONObject(jsonParams).toString()

        PendingListRepository.removeAccount(apiInterface, body)

    }

}