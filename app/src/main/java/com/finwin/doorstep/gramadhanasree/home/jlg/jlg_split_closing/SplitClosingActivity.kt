package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivitySplitClosingBinding

import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity
import com.finwin.doorstep.gramadhanasree.utils.CustomViewPager
import com.google.android.material.tabs.TabLayout

class SplitClosingActivity : BaseActivity() {

    lateinit var binding: ActivitySplitClosingBinding
    lateinit var viewModel: SplitClosingViewModel

    lateinit var tabs: TabLayout
    lateinit var viewPager: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this, R.layout.activity_split_closing)
        viewModel= ViewModelProvider(this)[SplitClosingViewModel::class.java]
        binding.viewModel=viewModel


        val sectionsPagerAdapter = PagerAdapter(
            this,
            supportFragmentManager
            //, viewModel.mAction
        )
        viewPager =binding.viewPager

        viewPager.adapter = sectionsPagerAdapter
        tabs =binding.tabs
        viewPager.offscreenPageLimit = 3

        tabs.setupWithViewPager(viewPager)

    }


    fun setTab(tabNumber: Int)
    {
        viewPager.setCurrentItem(tabNumber, true)
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