package com.finwin.doorstep.gramadhanasree.home.jlg.search_member

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.SearchCenterAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.action.SearchMemberAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object SearchMemberRepository {

    lateinit var mAction: MutableLiveData<SearchMemberAction>
    @SuppressLint("CheckResult")
    fun getSearchMember(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getSearchMember(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.customerList.data!=null) {
                            mAction.value = SearchMemberAction(
                                SearchMemberAction.GET_MEMBER_SUCCESS,
                                response
                            )
                        } else {
                            mAction.value = SearchMemberAction(SearchMemberAction.API_ERROR,response.customerList.error )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = SearchMemberAction(
                                SearchMemberAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = SearchMemberAction(
                                SearchMemberAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                SearchMemberAction(
                                    SearchMemberAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

}