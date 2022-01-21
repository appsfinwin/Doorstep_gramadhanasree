package com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group

import android.view.View
import android.widget.AdapterView
import androidx.databinding.*
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.Services
import  com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.login.pojo.getMasters.Data
import com.finwin.doorstep.gramadhanasree.utils.DataHolder
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject
import java.util.*

class SearchGroupAccountViewModel : ViewModel(), Observable {

    var repository : SearchGroupRepository = SearchGroupRepository().getInstance()
    var mAction: MutableLiveData<JlgAction> = MutableLiveData()
    var apiInterface = RetrofitClient().getApi()!!
    var branchId: ObservableField<String> = ObservableField("")
    var branchListData: ObservableArrayList<Data> =ObservableArrayList()
    var branchList: ObservableArrayList<String> =ObservableArrayList()
    var list: List<Data> = DataHolder.branchdetails
    var searchKey= ObservableField("")

    init {
        repository.mAction=mAction
        branchListData.clear()
        branchList.clear()
        branchList.add("--Select Branch--")
        branchListData.add(Data("-1","--Select Branch--","-1"))

        for (i in list.indices) {
            branchList.add(list[i].NAME)
            branchListData.add(list[i])
        }

    }
    private var selectedBranch = 0
    private val registry = PropertyChangeRegistry()

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
        branchId.set( branchListData[position].ID.toString())

    }
    public fun searchGroup() {

        val jsonParams: MutableMap<String?, Any?> = HashMap()

        jsonParams["Br_Code"] = branchId.get()
        jsonParams["Searchtype"] = "AccNo"
        jsonParams["Searchkey"] = searchKey.get()

        val body = RequestBody.create(
            "application/json; charset=utf-8".toMediaTypeOrNull(),
            JSONObject(jsonParams).toString()
        )


        repository.searchGroup(apiInterface, body)
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    public fun clickSubmit(view: View)
    {
        if (branchId.get().equals("-1"))
        {
            Services.showSnakbar("Please select a branch!",view)
        }else
        {
            searchGroup()
        }
    }
}