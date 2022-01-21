package com.finwin.doorstep.gramadhanasree.home.jlg.search_group

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivitySearchGroupBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.action.SearchGroupAction
import com.finwin.doorstep.gramadhanasree.home.jlg.search_group.adapter.SearchGroupAdapter
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity

class SearchGroupActivity : BaseActivity() {

    lateinit var viewModel: SearchGroupViewModel
    lateinit var binding: ActivitySearchGroupBinding
    lateinit var searchGroupAdapter: SearchGroupAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_group)
        viewModel = ViewModelProvider(this)[SearchGroupViewModel::class.java]
        binding.viewModel = viewModel

        binding.btnCancel.setOnClickListener { view ->
            finish()
        }

        setRecyclerView(binding.rvSearchGroup)
        viewModel.mAction.observe(this, Observer {
            viewModel.cancelLoading()
            when (it.action) {
                SearchGroupAction.SEARCH_GROUP_SUCCESS -> {
                    searchGroupAdapter.setGroupData(it.searchGroupResponse.data)
                    searchGroupAdapter.notifyDataSetChanged()
                }
                SearchGroupAction.GET_CENTER_GY_BRANCH_SUCCESS -> {
                    viewModel.setCenterData(it.getCenterByBranchResponse.data)
                }
                SearchGroupAction.API_ERROR -> {
                }
            }
        })
    }

    private fun setRecyclerView(rvSearchGroup: RecyclerView) {

        rvSearchGroup.setHasFixedSize(true)
        rvSearchGroup.layoutManager = LinearLayoutManager(this)
        searchGroupAdapter = SearchGroupAdapter()
        rvSearchGroup.adapter = searchGroupAdapter

        setObservable(searchGroupAdapter)

    }

    private fun setObservable(searchGroupAdapter: SearchGroupAdapter) {

        searchGroupAdapter.mAction.observe(this, Observer {
            when (it.action) {
                SearchGroupAction.CLICK_GROUP -> {
                    val intent = intent
                    intent.putExtra("group_code", it.searchGroupData.groupCode)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        })

    }
}