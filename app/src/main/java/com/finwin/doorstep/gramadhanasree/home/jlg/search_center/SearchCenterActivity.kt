package com.finwin.doorstep.gramadhanasree.home.jlg.search_center

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ActivitySearchCenterBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.search_center.adapter.SearchCenterAdapter
import com.finwin.doorstep.gramadhanasree.logout_listner.BaseActivity

class SearchCenterActivity : BaseActivity() {

    lateinit var binding: ActivitySearchCenterBinding
    lateinit var viewModel: SearchCenterViewModel
    lateinit var centerAdapter: SearchCenterAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_search_center)
        viewModel= ViewModelProvider(this)[SearchCenterViewModel::class.java]
        binding.viewmodel= viewModel

        setCenterList(binding.rvCenterList)

        viewModel.mRadioButton.observe(this, Observer {
            when(it)
            {
                "Center Name"->{binding.etSearch.editText?.inputType=InputType.TYPE_CLASS_TEXT}
                "Center Code"->{binding.etSearch.editText?.inputType=InputType.TYPE_CLASS_NUMBER}
            }
        })

        viewModel.mAction.observe(this, Observer {

            viewModel.cancelLoading()
            when(it.action)
            {
                SearchCenterAction.SEARCH_CENTER_SUCCESS->{

                    centerAdapter.setCenterData(it.getSearchCenterResponse.data)
                    centerAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun setCenterList(rvCenterList: RecyclerView) {
        rvCenterList.setHasFixedSize(true)
        rvCenterList.layoutManager= LinearLayoutManager(this)
        centerAdapter= SearchCenterAdapter()
        rvCenterList.adapter= centerAdapter
        setObserver(centerAdapter)
    }

    private fun setObserver(centerAdapter: SearchCenterAdapter) {
        centerAdapter.mAction.observe(this, Observer {

            val intent = intent
            intent.putExtra("centerName",it.centerData.centerName)
            intent.putExtra("centerCode",it.centerData.centerCode)
            setResult(Activity.RESULT_OK, intent)
            finish()

        })
    }
}