package com.finwin.doorstep.gramadhanasree.home.agent_management.change_password

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.home.agent_management.change_password.action.ChangePasswordAction
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChangePasswordRepository {

    lateinit var mAction: MutableLiveData<ChangePasswordAction>


    @SuppressLint("CheckResult")
    fun changePassword(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.changePassword(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.status.equals("1")) {
                        mAction.value = ChangePasswordAction(
                            ChangePasswordAction.CHANGE_PASSWORD_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = ChangePasswordAction(ChangePasswordAction.API_ERROR, response.msg)
                    }
                }, { error ->


                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = ChangePasswordAction(
                                ChangePasswordAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = ChangePasswordAction(
                                ChangePasswordAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                ChangePasswordAction(
                                    ChangePasswordAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }
}