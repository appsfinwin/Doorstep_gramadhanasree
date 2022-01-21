package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.closing_other_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.ClosingOtherDetailsFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_split_closing.SplitClosingActivity

class ClosingOtherDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = ClosingOtherDetailsFragment()
    }

    private lateinit var viewModel: ClosingOtherDetailsViewModel
    private lateinit var binding: ClosingOtherDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ClosingOtherDetailsViewModel::class.java)
        binding= DataBindingUtil.inflate(inflater,R.layout.closing_other_details_fragment, container, false)
        binding.viewModel=viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.totalAmount.set( (activity as SplitClosingActivity?)?.viewModel?.totalAmount?.get())

//        (activity as SplitClosingActivity?)?.viewModel?.initLoading(context)
//        (activity as SplitClosingActivity?)?.viewModel?.getCodeMasters()

        (activity as SplitClosingActivity?)?.viewModel?.mAction?.observe(viewLifecycleOwner, Observer {
            (activity as SplitClosingActivity?)?.viewModel?.cancelLoading()
            when(it.action){

                JlgAction.JLG_CODE_MASTERS_SUCCESS->
                {

                    it.codeMasterResponse?.let { it1 -> viewModel.setInstrumentType(it1.InstrumentType) }
                }

                JlgAction.SPLIT_TRANSACTION_SUCCESS->
                {
                    SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText("Success!")
                        .setContentText(it.splitTransactionResponse?.msg)
                        .setConfirmClickListener {
                            it.cancel()
                            viewModel.clearData()
                            (activity as SplitClosingActivity?)?.setTab(0)
                            (activity as SplitClosingActivity?)?.viewModel?.mAction?.value= JlgAction(
                                JlgAction.JLG_CLEAR_DATA)
                        }
                        .show()
                }
            }


        })



        (activity as SplitClosingActivity?)?.viewModel?.accountsLiveData?.observe(viewLifecycleOwner, Observer {
            (activity as SplitClosingActivity?)?.viewModel?.cancelLoading()
            viewModel.setAccountList(it)
            viewModel.totalAmount.set( (activity as SplitClosingActivity?)?.viewModel?.totalAmount?.get())
            //(activity as SplitClosingActivity?)?.viewModel?.setAccountData(it)

        })

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            when(it.action)
            {
            
                JlgAction.CLICK_SUBMIT_FROM_JLG_SPLIT_OTHER_DETAILS->
                {

                    (activity as SplitClosingActivity?)?.viewModel?.particulars?.set(viewModel.particulars.get())
                    (activity as SplitClosingActivity?)?.viewModel?.instrumentType?.set(viewModel.instrumenTypeSelected.get())
                    (activity as SplitClosingActivity?)?.viewModel?.instrumentType?.set(viewModel.instrumenTypeSelected.get())
                    (activity as SplitClosingActivity?)?.viewModel?.instrumentNumber?.set(viewModel.instrumentNumber.get())
                    (activity as SplitClosingActivity?)?.viewModel?.instrumentDate?.set(viewModel.submitInstrumentDate.get())
                    (activity as SplitClosingActivity?)?.viewModel?.totalAmount?.set(viewModel.totalAmount.get())

                    //(activity as SplitClosingActivity?)?.viewModel?.initLoading(context)

                    (activity as SplitClosingActivity?)?.viewModel?.callSpitTransactionApi(context)
                }
                JlgAction.CLICK_PREVIOUS_FROM_OTHER_DETAILS->{
                    (activity as SplitClosingActivity?)?.gotoPrevious()
                }
            }
        })

    }

  







}