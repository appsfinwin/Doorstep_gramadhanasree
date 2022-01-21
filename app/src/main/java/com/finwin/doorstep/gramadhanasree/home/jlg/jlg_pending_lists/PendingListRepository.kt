package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_pending_lists

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

object PendingListRepository {

    lateinit var mAction: MutableLiveData<JlgAction>

    @SuppressLint("CheckResult")
    fun getPendingList(apiInterface: ApiInterface, body: RequestBody?){
        val observable = apiInterface.getPendingList(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    try {
                        if (response != null) {
                            if (response.status.equals("1")) {
                                mAction.value = JlgAction(
                                    JlgAction.JLG_PENDING_LIST_SUCCESS,
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


    @SuppressLint("CheckResult")
    fun removeAccount(apiInterface: ApiInterface, body: RequestBody?){
        val observable = apiInterface.removeAccount(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    try {
                        if (response != null) {
                            if (response.status.equals("1")) {
                                mAction.value = JlgAction(
                                    JlgAction.REMOVE_ACCOUNT_SUCCESS,
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