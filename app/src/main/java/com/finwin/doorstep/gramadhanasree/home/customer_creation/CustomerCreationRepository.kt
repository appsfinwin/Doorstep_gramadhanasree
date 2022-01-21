package com.finwin.doorstep.gramadhanasree.home.customer_creation

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.customer_creation.action.CustomerCretaionAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class CustomerCreationRepository {

    lateinit var mAction: MutableLiveData<CustomerCretaionAction>
    lateinit var INSTANCE: CustomerCreationRepository
    public fun getInstance () : CustomerCreationRepository {
        if (!::INSTANCE.isInitialized) {
            INSTANCE= CustomerCreationRepository()
        }
        return INSTANCE

    }

    @SuppressLint("CheckResult")
    fun getRefCodes(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getReferenceCodes(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.data != null) {
                        mAction.value = CustomerCretaionAction(
                            CustomerCretaionAction.GET_REF_CODE_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = CustomerCretaionAction(
                            CustomerCretaionAction.API_ERROR,
                            response
                        )
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }

    @SuppressLint("CheckResult")
    fun getPostOffice(apiInterface: ApiInterface, body: String?) {
        val observable = apiInterface.getPostOffice(body)
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response?.Status == "Success") {
                        mAction.value = CustomerCretaionAction(
                            CustomerCretaionAction.GET_POST_OFFICE_SUCCESS,
                            response
                        )
                    } else {

                        if (response != null) {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.POST_OFFICE_ERROR,
                                response.Message
                            )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value =
                                CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    "Timeout! Please try again later"
                                )
                        }
                        is UnknownHostException -> {
                            mAction.value =
                                CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    "No Internet"
                                )
                        }
                        else -> {
                            mAction.value =
                                CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }

    @SuppressLint("CheckResult")
    fun customerCreation(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.customerCreation(body)
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    try {
                        if (response.data.Table1.isNotEmpty()) {
                            if (response.data.Table1[0].ReturnStatus == "Y") {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.CUSTOMER_CREATION_SUCCESS,
                                    response
                                )
                            } else if (response.data.Table1[0].ReturnStatus == "N") {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    response.data.Table1[0].ReturnMessage
                                )
                            }
                        } else {

                            if (response != null) {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    "Please try again!"
                                )
                            }
                        }
                    } catch (e: Exception) {
                        CustomerCretaionAction(CustomerCretaionAction.API_ERROR, e.localizedMessage)
                    }
                }, { error ->


                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }

    @SuppressLint("CheckResult")
    fun validateOtp(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.validateOtp(body)
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    try {

                            if (response?.otp?.status == "Y") {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.VALIDATE_OTP_SUCCESS,
                                    response
                                )
                            } else if (response?.otp?.status == "N") {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    response.otp.Msg
                                )

                        } else {

                            if (response != null) {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    "Please try again!"
                                )
                            }
                        }
                    } catch (e: Exception) {
                        CustomerCretaionAction(CustomerCretaionAction.API_ERROR, e.localizedMessage)
                    }
                }, { error ->

                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }

    @SuppressLint("CheckResult")
    fun createOtp(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.createOtp(body)
        observable?.subscribeOn(Schedulers.io())?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    try {

                            if (response?.otp?.status == "Y") {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.SENT_OTP_SUCCESS,
                                    response
                                )
                            } else if (response?.otp?.status == "N") {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    response.otp.Msg
                                )

                        } else {

                            if (response != null) {
                                mAction.value = CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    "Please try again!"
                                )
                            }
                        }
                    } catch (e: Exception) {
                        CustomerCretaionAction(CustomerCretaionAction.API_ERROR, e.localizedMessage)
                    }
                }, { error ->


                    when (error) {
                        is SocketTimeoutException -> {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                            mAction.value = CustomerCretaionAction(
                                CustomerCretaionAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                CustomerCretaionAction(
                                    CustomerCretaionAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }
                }
            )

    }
}