package com.finwin.doorstep.gramadhanasree.home.jlg.search_group

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.action.SearchGroupAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object SearchGroupRepository {

    lateinit var mAction : MutableLiveData<SearchGroupAction>

    @SuppressLint("CheckResult")
    fun getSearchGroup(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getSearchGroup(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.status.equals("1")) {
                            mAction.value = SearchGroupAction(
                                SearchGroupAction.SEARCH_GROUP_SUCCESS,
                                response
                            )
                        } else {

                            mAction.value = SearchGroupAction(
                                SearchGroupAction.API_ERROR,response.message )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = SearchGroupAction(
                                SearchGroupAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = SearchGroupAction(
                                SearchGroupAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                SearchGroupAction(
                                    SearchGroupAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }


    @SuppressLint("CheckResult")
    fun getCenterByBranch(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getCenterByBranch(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.status.equals("1")) {
                            mAction.value = SearchGroupAction(
                                SearchGroupAction.GET_CENTER_GY_BRANCH_SUCCESS,
                                response
                            )
                        } else {

                            mAction.value = SearchGroupAction(
                                SearchGroupAction.API_ERROR,"Something error" )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = SearchGroupAction(
                                SearchGroupAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = SearchGroupAction(
                                SearchGroupAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                SearchGroupAction(
                                    SearchGroupAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }
}