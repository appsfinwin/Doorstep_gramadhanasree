package com.finwin.doorstep.gramadhanasree.home.jlg.search_center

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object SearchCenterRepository {

    lateinit var mAction: MutableLiveData<SearchCenterAction>
    @SuppressLint("CheckResult")
    fun getSearchCenter(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getsearchCenter(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.status == "1") {
                            mAction.value = SearchCenterAction(
                                SearchCenterAction.SEARCH_CENTER_SUCCESS,
                                response
                            )
                        } else {

                            mAction.value = SearchCenterAction(SearchCenterAction.API_ERROR,response.msg )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = SearchCenterAction(
                                SearchCenterAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = SearchCenterAction(
                                SearchCenterAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                SearchCenterAction(
                                    SearchCenterAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }
}