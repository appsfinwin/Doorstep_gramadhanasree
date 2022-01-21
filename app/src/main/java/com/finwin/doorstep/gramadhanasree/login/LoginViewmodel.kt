package com.finwin.doorstep.gramadhanasree.login

import android.content.Context
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.login.action.LoginAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import io.reactivex.disposables.CompositeDisposable
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.lang.IllegalStateException
import java.util.*


public  class LoginViewmodel() : ViewModel() {

    var of_username = ObservableField("")
    var of_password = ObservableField("")
    var apiInterface: ApiInterface? = RetrofitClient().getApi()
    var repository: LoginRepository
    var compositeDisposable:CompositeDisposable
    var mAction: MutableLiveData<LoginAction>
    init {
        compositeDisposable= CompositeDisposable()
        mAction=MutableLiveData()
        repository= LoginRepository(mAction)
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


    fun clickLogin(view: View) {
        when {
            of_username.get() == "" -> {
                Services.showSnakbar("Username cannot be ampty", view)
            }
            of_password.get() == "" -> {
                Services.showSnakbar("Password cannot be ampty", view)
            }
            else -> {
                initLoading(view.context)
                loginApi()
            }
        }
    }

    private fun loginApi() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()
        jsonParams["username"] = of_username.get()
        jsonParams["password"] = of_password.get()


        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        apiInterface?.let { repository.login(it, body) }
    }

    fun getMasters() {
        apiInterface?.let { repository.getMasters(it) }
    }


    override fun onCleared() {
        super.onCleared()
        mAction.value= LoginAction(LoginAction.DEFAULT)
    }

    class LoginViewmodelFactory : ViewModelProvider.Factory{
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewmodel::class.java))
            {
                @Suppress("UNCHECKED_CAST")
                return LoginViewmodel() as T
            }
            throw IllegalStateException("unknown viewmodel class")
        }

    }

}