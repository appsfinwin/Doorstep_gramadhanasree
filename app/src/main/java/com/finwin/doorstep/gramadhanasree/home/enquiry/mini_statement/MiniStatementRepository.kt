package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.action.MiniStatementAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class MiniStatementRepository {
    lateinit var mAction: MutableLiveData<MiniStatementAction>

    @SuppressLint("CheckResult")
    fun getAccountHolder(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getAccountHolder(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.account.data != null) {
                        mAction.value = MiniStatementAction(
                            MiniStatementAction.GET_ACCOUNT_HOLDER_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = MiniStatementAction(MiniStatementAction.API_ERROR, response.account.error)
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = MiniStatementAction(
                                MiniStatementAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = MiniStatementAction(
                                MiniStatementAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                MiniStatementAction(
                                    MiniStatementAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

}