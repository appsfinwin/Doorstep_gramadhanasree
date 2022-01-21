package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.RequestBody

class JlgCentercreationRepository {

    lateinit var INSTANCE: JlgCentercreationRepository
    lateinit var mAction: MutableLiveData<JlgAction>
    public fun getInstance () : JlgCentercreationRepository {
        if (!::INSTANCE.isInitialized) {
            INSTANCE= JlgCentercreationRepository()
    }
        return INSTANCE

}

    @SuppressLint("CheckResult")
    fun getJlgCenter(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getJlgBranches(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.status.equals("1")) {
                        mAction.value = JlgAction(
                            JlgAction.JLG_GET_CENTER_SUCCESS,
                            response
                        )
                    } else {

                        mAction.value = JlgAction(JlgAction.API_ERROR, "Please try again")
                    }
                }, { error ->
                    mAction.value =
                        JlgAction(JlgAction.API_ERROR, error.message.toString())
                }
            )

    }

    @SuppressLint("CheckResult")
    fun jlgCreateCenter(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.jlgCreateCenter(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.status.equals("1")) {
                        mAction.value = JlgAction(
                            JlgAction.JLG_CREATE_CENTER,
                            response
                        )
                    } else {

                        mAction.value = JlgAction(JlgAction.API_ERROR,response.msg )
                    }
                }, { error ->
                    mAction.value =
                        JlgAction(JlgAction.API_ERROR, error.message.toString())
                }
            )

    }
 @SuppressLint("CheckResult")
    fun jlgUpdateCenter(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.jlgUpdateCenter(body)
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    if (response.status.equals("1")) {
                        mAction.value = JlgAction(
                            JlgAction.JLG_UPDATE_CENTER,
                            response
                        )
                    } else {

                        mAction.value = JlgAction(JlgAction.API_ERROR,response.msg )
                    }
                }, { error ->
                    mAction.value =
                        JlgAction(JlgAction.API_ERROR, error.message.toString())
                }
            )

    }
}