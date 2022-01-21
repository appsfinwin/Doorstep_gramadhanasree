package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivityJlgLoanCreationBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_creation_pager_adapter.JlgPagerAdapter
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants
import com.finwin.doorstep.gramadhanasree.utils.CustomViewPager
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationViewModel
import com.google.android.material.tabs.TabLayout

class JlgLoanCreationActivity : BaseActivity() {
    lateinit var tabs: TabLayout
    lateinit var viewPager: CustomViewPager
    lateinit var viewModel: JlgLoanCreationViewModel
    lateinit var binding: ActivityJlgLoanCreationBinding

    companion object {
        lateinit var sharedPreferences: SharedPreferences
        lateinit var editor: SharedPreferences.Editor
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_jlg_loan_creation)
        viewModel = ViewModelProvider(this)[JlgLoanCreationViewModel::class.java]
        binding.viewModel = viewModel

        //viewModel.initLoading(this)
        viewModel.getJlgProducts()
        viewModel.custId.set(sharedPreferences.getString(Constants.AGENT_ID,""))
        viewModel.branchCode.set(sharedPreferences.getString(Constants.BRANCH_ID,""))

        viewModel.mAction.observe(this, Observer {
            viewModel.cancelLoading()
            when(it.action)
            {
                JlgLoanCreationAction.GET_JLG_PRODUCTS_SUCCESS->{
                    it.getJlgProductResponse?.let { it1 -> viewModel.setProductData(it1.data) }
                }
                JlgLoanCreationAction.API_ERROR->{}

            }
        })

        viewModel.initLoading(this)
        viewModel.getCodeMasters()

        viewModel.mSchemeCode.observe(this, Observer {

            editor.putString("JLG_SCHEME_CODE",it)
            editor.apply()
            editor.commit()
            viewModel.initLoading(this)
            viewModel.getLoanPeriod(it)

        })

        val sectionsPagerAdapter = JlgPagerAdapter(
            this,
            supportFragmentManager
            //, viewModel.mAction
        )
        viewPager = binding.viewPager

        viewPager.adapter = sectionsPagerAdapter
        tabs = binding.tabs
        viewPager.offscreenPageLimit = 3

        tabs.setupWithViewPager(viewPager)

        viewPager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {

                //String custId=sharedPreferences.getString(ConstantClass.CUST_ID,"");
                if (position==0){
                    editor.putString(Constants.JLG_SCHEME_CODE,"")
                    editor.apply()
                }
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })
        disableTab(0)
        disableTab(1)
        disableTab(2)

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


    fun gotoNext() {
        viewPager.setCurrentItem(getItem(+1), true)
    }

    fun gotoPrevious() {
        viewPager.setCurrentItem(getItem(-1), true)
    }

    private fun getItem(i: Int): Int {
        return viewPager.currentItem + i
    }

    fun setTab(tabNumber: Int)
    {
        viewPager.setCurrentItem(tabNumber, true)
    }

    override fun onBackPressed() {
       exitDialog()
    }

    private fun exitDialog() {


        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Exit?")
            .setMessage("All the details entered will be lost!")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which ->
                    finish()
                })
            .setNegativeButton("No", null)
            .show()

    }

}