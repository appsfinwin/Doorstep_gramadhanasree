package com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.search_loan_number.action.SearchLoanAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object SearchLoanNumberRepository
{
    lateinit var mAction: MutableLiveData<SearchLoanAction>
    var job :CompletableJob? = null

//    fun getLoanNumbers(requestBody: RequestBody,apiInterface: ApiInterface) : LiveData<GetLoanNumbers> {
//        job= Job()
//        return  object : LiveData<GetLoanNumbers>(){
//
//            override fun onActive() {
//                super.onActive()
//                job?.let {
//                    CoroutineScope(IO + it).launch {
//                        val result=apiInterface.getSearchLoanNumbers(requestBody)
//                        withContext(Main){
//                            value= result
//                            it.complete()
//                        }
//                    }
//                }
//            }
//        }
    //  }

    @SuppressLint("CheckResult")
    fun getLoanNumbers(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getSearchLoanNumbers(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response?.customerList?.data!= null ) {
                        mAction.value = SearchLoanAction(
                            SearchLoanAction.SEARCH_SUCCESS,
                            response
                        )
                    } else {
                        var  error=response?.customerList?.error
                        mAction.value = SearchLoanAction(SearchLoanAction.API_ERROR, error)
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = SearchLoanAction(
                                SearchLoanAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = SearchLoanAction(
                                SearchLoanAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                SearchLoanAction(
                                    SearchLoanAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

    @SuppressLint("CheckResult")
    fun getLoanSchemes(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getLoanScheme(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response?.scheme?.size!! >0 ) {
                        mAction.value = SearchLoanAction(
                            SearchLoanAction.GET_SCHEME_SUCCESS,
                            response
                        )
                    } else {
                        // var  error=response.customerList.error
                        mAction.value = SearchLoanAction(SearchLoanAction.API_ERROR, "No Data Available")
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = SearchLoanAction(
                                SearchLoanAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = SearchLoanAction(
                                SearchLoanAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                SearchLoanAction(
                                    SearchLoanAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

    fun cancelJobs()
    {
        job?.cancel()
    }
}