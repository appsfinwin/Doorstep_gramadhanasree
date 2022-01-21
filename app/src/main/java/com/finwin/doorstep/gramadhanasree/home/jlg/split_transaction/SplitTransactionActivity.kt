package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivitySplitTransactionBinding
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity
import com.finwin.doorstep.gramadhanasree.utils.CustomViewPager

class SplitTransactionActivity : BaseActivity() {
    lateinit var tabs: TabLayout
    lateinit var viewPager: CustomViewPager
    lateinit var viewModel: SplitTransactionViewmodel
    lateinit var binding: ActivitySplitTransactionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        binding= DataBindingUtil.setContentView(this, R.layout.activity_split_transaction)
        viewModel=ViewModelProvider(this)[SplitTransactionViewmodel::class.java]
        binding.viewModel=viewModel


        val sectionsPagerAdapter = SectionsPagerAdapter(
            this,
            supportFragmentManager
            //, viewModel.mAction
        )
        viewPager =binding.viewPager

        viewPager.adapter = sectionsPagerAdapter
        tabs =binding.tabs
        viewPager.offscreenPageLimit = 3

        tabs.setupWithViewPager(viewPager)

//        viewModel.agentId.set(sharedPreferences.getString(Constants.AGENT_ID,""))
//        viewModel.branchCode.set(sharedPreferences.getString(Constants.BRANCH_ID,""))

    }

    fun disableTab(tabNumber: Int) {
        val vg = tabs.getChildAt(0) as ViewGroup
        val vgTab = vg.getChildAt(tabNumber) as ViewGroup
        vgTab.isEnabled = false
        viewPager.setPagingEnabled(false)
    }

    fun enableTab(tabNumber: Int) {
        val vg = tabs.getChildAt(0) as ViewGroup
        val vgTab = vg.getChildAt(tabNumber) as ViewGroup
        vgTab.isEnabled = true
        viewPager.setPagingEnabled(true)
    }


    fun setTab(tabNumber: Int)
    {
        viewPager.setCurrentItem(tabNumber, true)
    }

    fun gotoNext() {
        viewPager.setCurrentItem(getItem(+1), true)
    }
    fun gotoPrevious() {
        viewPager.setCurrentItem(getItem(-1), true)
    }
    private fun getItem(i: Int): Int {
        return viewPager.currentItem + i
    }

//    public fun getSelectedAccountsLiveData():ObservableArrayList<Dat>
//    {
//        //viewModel.selectedAccountsData.value=viewModel.selectedAccountsObservable
//        return viewModel.selectedAccountsObservable
//    }
//    public fun insertData(dat: Dat)
//    {
//        //OtherDetailsFragment.newInstance().insertData(dat)
//        viewModel.selectedAccountsObservable.add(dat)
//    }
//
//    public fun removeData(dat: Dat)
//    {
//        if ( viewModel.selectedAccountsObservable.contains(dat)){
//        viewModel.selectedAccountsObservable.remove(dat)}
//            //OtherDetailsFragment.newInstance().removeData(dat)
//
//    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Exit?")
            .setMessage("saved data will be lost?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    super.onBackPressed()
                })
            .setNegativeButton("No", null)
            .show()
    }
}