package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group

import android.annotation.SuppressLint
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.action.SelectGroupAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.Member
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.MemberDao
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class JlgSelectGroupRepository( val memberDao: MemberDao) {

    var mAction: MutableLiveData<SelectGroupAction> = MutableLiveData()

    //val allMembers : Flow<List<Member>> =
    suspend fun allMembers(): Flow<List<Member>> {
        //memberDao.deleteAll()
            return  memberDao.getAllMembers()
        }


    val member: Flow<Member> = memberDao.getMember()

    @SuppressLint("CheckResult")
    fun getGroup(apiInterface: ApiInterface, body: RequestBody?) {
        val observable = apiInterface.getGroup(body)
        observable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe(
                { response ->
                    if (response != null) {
                        if (response.status.equals("1")) {
                            mAction.value = SelectGroupAction(
                                SelectGroupAction.GET_GROUP_SELECT_SUCCESS,
                                response
                            )
                        } else {

                            mAction.value = SelectGroupAction(
                                SelectGroupAction.API_ERROR,response.message )
                        }
                    }
                }, { error ->
                    when (error) {
                        is SocketTimeoutException -> {
                           mAction.value = SelectGroupAction(
                               SelectGroupAction.API_ERROR,
                                "Timeout! Please try again later"
                            )
                        }
                        is UnknownHostException -> {
                           mAction.value = SelectGroupAction(
                               SelectGroupAction.API_ERROR,
                                "No Internet"
                            )
                        }
                        else -> {
                            mAction.value =
                                SelectGroupAction(
                                    SelectGroupAction.API_ERROR,
                                    error.message.toString()
                                )
                        }
                    }

                }
            )

    }

    @SuppressWarnings
    @WorkerThread
    suspend fun insert(member: Member)
    {
        memberDao.insert(member)
    }

    @SuppressWarnings
    @WorkerThread
    suspend fun clearData()
    {
        memberDao.deleteAll()
    }

    @SuppressWarnings
    @WorkerThread
    suspend fun updateMember(member: Member)
    {
        memberDao.updateMember(member)
    }

    @SuppressWarnings
    @WorkerThread
    suspend fun getDisbursementAmount():List<Member>
    {
        return memberDao.getDisbursementAmount()
    }



}