package com.finwin.doorstep.gramadhanasree.home.transactions.search_account

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.adapter.SearchAdapter

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivitySearchBinding
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity

class SearchActivity : BaseActivity() {

    lateinit var binding: ActivitySearchBinding
    lateinit var viewmodel: SearchViewmodel
    lateinit var adapter: SearchAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding=DataBindingUtil.setContentView(this, R.layout.activity_search)
        viewmodel=ViewModelProvider(this).get(SearchViewmodel::class.java)
        binding.viewmodel=viewmodel


        setRecyclerview(binding.rvAccounts)

        viewmodel.mAction.observe(this, Observer {

            viewmodel.cancelLoading()
            when(it.action)
            {
                SearchAction.SEARCH_ACCOUNT_SUCCESS ->{
                    viewmodel.cancelLoading()
                    setRecyclerview(binding.rvAccounts)
                    adapter.setSearchData(it.searchAccountResponse.customer_list.data)
                    adapter.notifyDataSetChanged()

                }

                SearchAction.API_ERROR ->
                {
                    adapter.EmptyList()
                    adapter.notifyDataSetChanged()
                    Toast.makeText(this,it.searchAccountResponse.customer_list.error,Toast.LENGTH_LONG).show()
                }

                SearchAction.CLICK_CANCEL ->
                {
                    finish()
                }
            }
        })

    }

    private fun setRecyclerview(rvAccounts: RecyclerView) {

        adapter= SearchAdapter()
        rvAccounts.setLayoutManager(LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false));
        rvAccounts.adapter=adapter

        setObserver(adapter)
    }

    private fun setObserver(adapter: SearchAdapter) {

        adapter.mAction.observe(this, Observer {

            when(it.action)
            {
                SearchAction.CLICK_ACCOUNT ->
                {
                    val intent = intent
                    intent.putExtra("account_number", it.searchData.ACC_NO)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}