package com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.reciept

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.enquiry.mini_statement.action.MiniStatementAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MiniStatementRecieptRepository {

    lateinit var mAction: MutableLiveData<MiniStatementAction>

    @SuppressLint("CheckResult")
    fun getAccountHolder(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.miniStatement(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.mini_statement != null) {
                        mAction.value = MiniStatementAction(
                            MiniStatementAction.GET_MINI_STATEMENT_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = MiniStatementAction(MiniStatementAction.API_ERROR, response)
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