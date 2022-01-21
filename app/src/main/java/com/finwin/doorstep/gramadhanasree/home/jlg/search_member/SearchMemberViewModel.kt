package com.finwin.doorstep.gramadhanasree.home.jlg.search_member

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.AdapterView
import androidx.databinding.*
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.SearchCenterAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.SearchCenterRepository
import com.finwin.doorstep.gramadhanasree.home.jlg.search_member.action.SearchMemberAction
import com.finwin.doorstep.gramadhanasree.login.pojo.getMasters.Data
import com.finwin.doorstep.gramadhanasree.retrofit.ApiInterface
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.DataHolder
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.HashMap

class SearchMemberViewModel(application: Application) : AndroidViewModel(application) , Observable {


    var branchList = ObservableArrayList<Any>()
    var branchListData = ObservableArrayList<Data>()
    private var selectedBranch = 0
    private val registry = PropertyChangeRegistry()
    var apiInterface = RetrofitClient().getApi()!!
    var etCustId =ObservableField("")
    var branchId =ObservableField("")

    var sharedPreferences : SharedPreferences =
        application.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
    var mAction: MutableLiveData<SearchMemberAction> = MutableLiveData()

    var loading: SweetAlertDialog? = null
    fun initLoading(context: Context?) {
        loading = Services.showProgressDialog(context)
    }

    fun cancelLoading() {
        if (loading != null) {
            loading!!.cancel()
            loading = null
        }
    }
    init {
        SearchMemberRepository.mAction= mAction
        branchList.add("--Select Branch--")
        branchListData.add(Data("-1","","--Select Branch--"))

        for (branch in DataHolder.branchdetails) {
            branchList.add(branch.NAME)
            branchListData.add(branch)
        }
    }

    @Bindable
    fun getSelectedBranch(): Int {
        return selectedBranch
    }

    fun setSelectedBranch(selectedDistrict: Int) {
        this.selectedBranch = selectedDistrict
        registry.notifyChange(this, BR.selectedBranch)
    }

    fun onSelectedBranch(parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {

        branchId.set(branchListData.get(position).ID.toString())

    }


    fun getSearchMember() {
        val jsonParams: MutableMap<String?, Any?> = HashMap()

       // jsonParams["branch_id"] = sharedPreferences.getString(Constants.BRANCH_ID, "")
        jsonParams["branch_id"] = branchId.get()
        jsonParams["agent_id"] =  sharedPreferences.getString(Constants.AGENT_ID, "")
        jsonParams["custid"] = etCustId.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )
        var request = (jsonParams).toString()
        request = (jsonParams).toString()

        SearchMemberRepository.getSearchMember(apiInterface = apiInterface, body = body)
    }


    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
       registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    fun clickSubmit(view:View)
    {
        initLoading(view.context)
        getSearchMember()
    }
}