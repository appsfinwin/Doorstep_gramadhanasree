package com.finwin.doorstep.gramadhanasree.home.jlg.search_group

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.*
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.action.SearchGroupAction
import com.finwin.doorstep.gramadhanasree.login.pojo.getMasters.Data
import com.finwin.doorstep.gramadhanasree.retrofit.RetrofitClient
import com.finwin.doorstep.gramadhanasree.utils.DataHolder
import com.finwin.doorstep.gramadhanasree.utils.Services
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import org.json.JSONObject

class SearchGroupViewModel : ViewModel(), Observable {


    var branchList = ObservableArrayList<String>()
    var branchListData = ObservableArrayList<Data>()
    private var branchId: ObservableField<String> = ObservableField("")
    private var centerCode: ObservableField<String> = ObservableField("")
    private var selectedBranch = 0
    var apiInterface = RetrofitClient().getApi()!!

    var centerList = ObservableArrayList<String>()
    var centerListData = ObservableArrayList<com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.CenterData>()

    private var selectedCenter = 0
    var searchKey= ObservableField("")

    private val registry = PropertyChangeRegistry()
    var mAction: MutableLiveData<SearchGroupAction> = MutableLiveData()

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
        SearchGroupRepository.mAction = mAction
        branchList.add("--Select Branch--")
        branchListData.add(Data("-1", "", "--Select Branch--"))

        centerList.add("--Select Center--")
        centerListData.add(com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.CenterData("","","","","",""))

        for (branch in DataHolder.branchdetails) {
            branchList.add(branch.NAME)
            branchListData.add(branch)
        }
    }

    @Bindable
    fun getSelectedCenter(): Int {
        return selectedCenter
    }

    fun setSelectedCenter(selectedCenter: Int) {
        this.selectedCenter = selectedCenter
        registry.notifyChange(this, BR.selectedBranch)
    }

    fun onSelectedCenter(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {

        centerCode .set(centerListData.get(position).centerCode.toString())

    }


    @Bindable
    fun getSelectedBranch(): Int {
        return selectedBranch
    }

    fun setSelectedBranch(selectedDistrict: Int) {
        this.selectedBranch = selectedDistrict
        registry.notifyChange(this, BR.selectedBranch)
    }

    fun onSelectedBranch(
        parent: AdapterView<*>?, view: View?, position: Int, id: Long
    ) {


        if (position!=0)
        {
            branchId .set( branchListData.get(position).ID)
            initLoading(view?.context)
            branchId.get()?.let { getCenterByBranch(it) }
        }

    }


    public fun searchGroup() {

        try {

            val params = JSONObject()
            params.put("BrCode", branchId.get())
            params.put("CenterCode", centerCode.get())

            var request = (params).toString()
            request = (params).toString()
            val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                params.toString()
            )

            SearchGroupRepository.getSearchGroup(apiInterface, body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun clickSubmit(view:View)
    {
        if (branchId.get().equals(""))
        {
            Toast.makeText(view.context, "Please select a branch", Toast.LENGTH_SHORT).show()
        }else if (centerCode.get().equals(""))
        {
            Toast.makeText(view.context, "Please select a center", Toast.LENGTH_SHORT).show()
        }else
        {
            initLoading(view.context)
            searchGroup()
        }
    }

    public fun getCenterByBranch( branchCode: String) {

        try {

            val params = JSONObject()
            params.put("Brcode",branchCode )

            var request = (params).toString()
            request = (params).toString()
            val body = RequestBody.create(
                "application/json; charset=utf-8".toMediaTypeOrNull(),
                params.toString()
            )
            SearchGroupRepository.getCenterByBranch(apiInterface, body)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.add(callback)
    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {
        registry.remove(callback)
    }

    fun setCenterData(data: List<com.finwin.doorstep.gramadhanasree.home.jlg.search_group.pojo.CenterData>) {
        for (i in data.indices)
        {
            centerList.add(data[i].centerName)
            centerListData.add(data[i])
        }
    }
}