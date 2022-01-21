package com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group

import android.app.Activity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivitySearchGroupAccountBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_account_group.adapter.GroupSearchAdapter
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity

class SearchGroupAccountActivity : BaseActivity() {

    lateinit var accountViewModel: SearchGroupAccountViewModel
    lateinit var binding: ActivitySearchGroupAccountBinding
    lateinit var adapter: GroupSearchAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_group_account)
        accountViewModel = ViewModelProvider(this)[SearchGroupAccountViewModel::class.java]
        binding.viewmodel = accountViewModel

        setRecyclerview(binding.rvGroupAccounts)

        accountViewModel.mAction.observe(this, Observer {
            when (it.action) {
                JlgAction.JLG_GET_GROUP_ACCOUNT_SUCCESS -> {

                    it.searchGroupAccountResponse?.let { it1 -> adapter.setSearchData(it1.data) }
                    adapter.notifyDataSetChanged()

                }
            }
        })
    }

    private fun setRecyclerview(rvGroupAccounts: RecyclerView) {

        rvGroupAccounts.layoutManager = LinearLayoutManager(this)
        rvGroupAccounts.setHasFixedSize(true)
        adapter = GroupSearchAdapter()
        rvGroupAccounts.adapter = adapter
        observeAdapter(adapter)
    }

    private fun observeAdapter(adapter: GroupSearchAdapter) {

        adapter.mAction.observe(this, Observer {
            when (it.action) {
                JlgAction.JLG_CLICK_GROUP_ACCOUNT -> {
                    val intent = intent
                    intent.putExtra("account_number", it.groupAccountData?.Ln_GlobalAccNo)
                    setResult(Activity.RESULT_OK, intent)
                    finish()

                }
            }
        })

    }
}