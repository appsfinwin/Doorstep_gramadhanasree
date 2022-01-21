package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.lang.Exception
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class SplitClosingRepository {
    public lateinit var INSTANCE: SplitClosingRepository

    public fun getInstance(): SplitClosingRepository
    {
        if (!:: INSTANCE.isInitialized)
        {
            INSTANCE= SplitClosingRepository()
        }

        return INSTANCE
    }

    lateinit var mAction : MutableLiveData<JlgAction>

    @SuppressLint("CheckResult")
    fun getCodeMasters(apiInterface: ApiInterface) {
        val observable = apiInterface.getCodeMasters()
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    try {
                        if (response.status.equals("1")) {
                            mAction.value = JlgAction(
                                JlgAction.JLG_CODE_MASTERS_SUCCESS,
                                response
                            )
                        } else {

                            mAction.value = JlgAction(JlgAction.API_ERROR, "Something error")
                        }
                    }catch (e: Exception)
                    {
                        mAction.value = JlgAction(JlgAction.API_ERROR, e.localizedMessage)
                    }
                }, { error ->
                    mAction.value =
                        JlgAction(JlgAction.API_ERROR, error.message.toString())
                }
            )

    }

    @SuppressLint("CheckResult")
    fun groupAccountDetails(apiInterface: ApiInterface, body: RequestBody?){
        val observable = apiInterface.groupAccountDetails(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    try {


                        if (response.status.equals("1")) {
                            mAction.value = JlgAction(
                                JlgAction.JLG_GET_GROUP_ACCOUNT_DETAILS_SUCCESS,
                                response
                            )
                        } else {
                            mAction.value = JlgAction(JlgAction.API_ERROR, response.msg)
                        }
                    }catch (e: Exception)
                    {
                        mAction.value = JlgAction(
                            JlgAction.API_ERROR,
                            e.localizedMessage)
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value =
                                JlgAction(
                                    JlgAction.API_ERROR,
                                    "Timeout! Please try again later"
                                )
                        }
                        is UnknownHostException -> {
                            mAction.value =
                                JlgAction(
                                    JlgAction.API_ERROR,
                                    "No Internet"
                                )
                        }
                        else -> {
                            when (error) {
                                is SocketTimeoutException -> {
                                    mAction.value = JlgAction(
                                        JlgAction.API_ERROR,
                                        "Timeout! Please try again later"
                                    )
                                }
                                is UnknownHostException -> {
                                    mAction.value = JlgAction(
                                        JlgAction.API_ERROR,
                                        "No Internet"
                                    )
                                }
                                else -> {
                                    mAction.value =
                                        JlgAction(
                                            JlgAction.API_ERROR,
                                            error.message.toString()
                                        )
                                }
                            }
                        }
                    }
                }
            )

    }


    @SuppressLint("CheckResult")
    fun splitTransaction(apiInterface: ApiInterface, body: RequestBody?){
        val observable = apiInterface.jlgSplitTransaction(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    try {
                        if (response != null) {
                            if (response.status.equals("1")) {
                                mAction.value = JlgAction(
                                    JlgAction.SPLIT_TRANSACTION_SUCCESS,
                                    response
                                )
                            } else {
                                mAction.value = JlgAction(JlgAction.API_ERROR, response.msg)
                            }
                        }
                    }catch (e: Exception)
                    {
                        mAction.value = JlgAction(
                            JlgAction.API_ERROR,
                            e.localizedMessage)
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value =
                                JlgAction(
                                    JlgAction.API_ERROR,
                                    "Timeout! Please try again later"
                                )
                        }
                        is UnknownHostException -> {
                            mAction.value =
                                JlgAction(
                                    JlgAction.API_ERROR,
                                    "No Internet"
                                )
                        }
                        else -> {
                            when (error) {
                                is SocketTimeoutException -> {
                                    mAction.value = JlgAction(
                                        JlgAction.API_ERROR,
                                        "Timeout! Please try again later"
                                    )
                                }
                                is UnknownHostException -> {
                                    mAction.value = JlgAction(
                                        JlgAction.API_ERROR,
                                        "No Internet"
                                    )
                                }
                                else -> {
                                    mAction.value =
                                        JlgAction(
                                            JlgAction.API_ERROR,
                                            error.message.toString()
                                        )
                                }
                            }
                        }
                    }
                }
            )

    }
}