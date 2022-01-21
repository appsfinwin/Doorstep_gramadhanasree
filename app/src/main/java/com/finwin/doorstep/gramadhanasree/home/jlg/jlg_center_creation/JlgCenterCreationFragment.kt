package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog

import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.JlgCenterCreationViewModel
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.JlgCenterCreationFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_center_creation.adapter.JlgCenterAdapter

class JlgCenterCreationFragment : Fragment() {

    companion object {
        fun newInstance() = JlgCenterCreationFragment()
    }

    private lateinit var viewModel: JlgCenterCreationViewModel
    private lateinit var binding: JlgCenterCreationFragmentBinding
    private lateinit var adapter: JlgCenterAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(JlgCenterCreationViewModel::class.java)
        binding=DataBindingUtil.inflate(inflater,
            R.layout.jlg_center_creation_fragment, container, false)
        binding.viewModel=viewModel

        viewModel.initLoading(activity)
        viewModel.getJlgCenter()

        setupRecyclerview(binding.rvCenterList)
        return binding.root
    }

    private fun setupRecyclerview(rvCenterList: RecyclerView) {

        adapter= JlgCenterAdapter()
        rvCenterList.layoutManager=LinearLayoutManager(activity)
        rvCenterList.setHasFixedSize(true)
        rvCenterList.adapter=adapter
        observeAdapter(adapter)
    }

    private fun observeAdapter(adapter: JlgCenterAdapter) {

        adapter.mAction.observe(viewLifecycleOwner, Observer {

            when(it.action)
            {
                JlgAction.JLG_CLICK_EDIT_CENTER->{
                    binding.etCenterCode.editText?.isEnabled=false
                    binding.etCenterCode.editText?.setText(it.centerData?.CenterCode)
                    binding.etCenterName.editText?.setText(it.centerData?.CenterName)
                }
                JlgAction.JLG_CLICK_DELETE_CENTER->{}
            }
        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

            viewModel.mAction.observe(viewLifecycleOwner, Observer {
           viewModel.cancelLoading()
           when(it.action)
           {
                JlgAction.JLG_GET_CENTER_SUCCESS->
                {
                    it.jlgCenterResponse?.let { it1 -> adapter.setCenterData(it1.data) }

                    it.jlgCenterResponse?.let { it1 -> adapter.setCenterData(it1.data) }
                    adapter.notifyDataSetChanged()
                }
               JlgAction.JLG_CREATE_CENTER->
               {
//                   SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE)
//                       .setTitleText("ERROR!")
//                       .setContentText()
//                       .setConfirmClickListener {
//
//                       }.setCancelClickListener {
//                           it.cancel()
//                       }
//                       .show()

                   SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                       .setTitleText("SUCCESS!")
                       .setContentText(it.jlgCreateCenterResponse?.msg)
                       .setConfirmClickListener {
                           it.cancel()
                           viewModel.initLoading(it.context)
                           viewModel.getJlgCenter()
                       }
                       .show()


               }
               JlgAction.API_ERROR->{
                   SweetAlertDialog(activity, SweetAlertDialog.ERROR_TYPE)
                       .setTitleText("ERROR!")
                       .setContentText(it.error)
                       .show()
               }

               JlgAction.JLG_UPDATE_CENTER->
               {
                   SweetAlertDialog(activity, SweetAlertDialog.SUCCESS_TYPE)
                       .setTitleText("SUCCESS!")
                       .setContentText(it.jlgCreateCenterResponse?.msg)
                       .setConfirmClickListener {
                           it.cancel()
                           viewModel.initLoading(it.context)
                           viewModel.getJlgCenter()
                       }
                       .show()
                   viewModel.resetText()
               }
           }
       })
    }

    override fun onStop() {
        super.onStop()
        viewModel.mAction.value= JlgAction(JlgAction.DEFAULT)
    }

}