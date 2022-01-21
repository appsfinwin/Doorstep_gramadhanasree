package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_group_creation

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object JlgGroupCreationRepository {

    lateinit var mAction: MutableLiveData<GroupCreationAction>
    @SuppressLint("CheckResult")
    fun createGroup(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.createGroup(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.status.equals("1")) {
                            mAction.value = GroupCreationAction(
                                GroupCreationAction.CREATE_GROUP_SUCCESS,
                                response
                            )
                        } else {

                            mAction.value = GroupCreationAction(GroupCreationAction.API_ERROR,response.message )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = GroupCreationAction(
                                GroupCreationAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = GroupCreationAction(
                                GroupCreationAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                GroupCreationAction(
                                    GroupCreationAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }
}