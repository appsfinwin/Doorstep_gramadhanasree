package com.finwin.doorstep.gramadhanasree.home.agent_management.change_password


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
import com.finwin.doorstep.gramadhanasree.utils.Constants

import com.finwin.doorstep.gramadhanasree.databinding.ChangePasswordFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.agent_management.change_password.action.ChangePasswordAction

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap

class ChangePasswordViewModel(application: Application) : AndroidViewModel(application) {

    var sharedPreferences: SharedPreferences = application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor = sharedPreferences.edit()

    var repository: ChangePasswordRepository = ChangePasswordRepository()
    var mAction :MutableLiveData<ChangePasswordAction> = MutableLiveData()

    var currentPassword: ObservableField<String> =ObservableField("")
    var newPassword: ObservableField<String> =ObservableField("")
    var reEnterNewPassword: ObservableField<String> = ObservableField("")
    var apiInterface = RetrofitClient().getApi()!!
    lateinit var binding: ChangePasswordFragmentBinding
    init {
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
    public fun changePassword() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()

        jsonParams["userid"] = sharedPreferences.getString(Constants.AGENT_ID, "")
        jsonParams["oldPassword"] = currentPassword.get().toString()
        jsonParams["new_password"] = newPassword.get().toString()
        jsonParams["re_password"] = reEnterNewPassword.get().toString()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )

        repository.changePassword(apiInterface, body)
    }

    public fun clickSubmit(view : View)
    {
        if (currentPassword.get().equals(""))
        {
            Services.showSnakbar("Current password cannot be empty",view)
            binding.etCurrentPassword.editText?.error="Current password cannot be empty"
        }else if (newPassword.get().equals(""))
        {
            Services.showSnakbar("New password cannot be empty",view)
            binding.etNewPassword.editText?.error="New password cannot be empty"
        }else if (reEnterNewPassword.get().equals(""))
        {
            Services.showSnakbar("Please re-enter new password",view)
            binding.etReEnterPassword.editText?.error="Please re-enter new password"
        }else
        {
            initLoading(view.context)
            changePassword()
        }

    }


    public fun reset() {
        currentPassword.set("")
        newPassword.set("")
        reEnterNewPassword.set("")

    }

    fun setDataBinding(bindin: ChangePasswordFragmentBinding?) {
        if (bindin != null) {
            this.binding=bindin
        }

    }
}