package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.other_details

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.OtherDetailsFragmentBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.JlgAction
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.SplitTransactionActivity
import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.other_details.adapter.ChargesAdapter
import com.finwin.doorstep.gramadhanasree.home.transactions.search_account.SearchActivity
import com.finwin.doorstep.gramadhanasree.utils.Constants

class   OtherDetailsFragment : Fragment() {

    companion object {
        fun newInstance() =
            OtherDetailsFragment()
    }

    private lateinit var viewModel: OtherDetailsViewModel
    private lateinit var binding: OtherDetailsFragmentBinding
    lateinit var chargesAdapter: ChargesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProviders.of(this).get(OtherDetailsViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.other_details_fragment, container, false)
        binding.viewModel = viewModel
        setRecyclerView(binding.rvChargeList)
        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        viewModel.totalAmount.set((activity as SplitTransactionActivity?)?.viewModel?.totalAmount?.get())

        (activity as SplitTransactionActivity?)?.viewModel?.initLoading(context)
        (activity as SplitTransactionActivity?)?.viewModel?.getCodeMasters()

        (activity as SplitTransactionActivity?)?.viewModel?.mAction?.observe(
            viewLifecycleOwner,
            Observer {
                (activity as SplitTransactionActivity?)?.viewModel?.cancelLoading()
                when (it.action) {

                    JlgAction.JLG_CODE_MASTERS_SUCCESS -> {
                        it.codeMasterResponse?.let { it1 -> viewModel.setCharges(it1.Charges) }
                        it.codeMasterResponse?.let { it1 -> viewModel.setInstrumentType(it1.InstrumentType) }
                    }


                    JlgAction.UPDATE_ACCOUNTS_DATA -> {
                        viewModel.setAccountList(it.accountsListData)

                    }

                    JlgAction.SPLIT_TRANSACTION_SUCCESS -> {
                        SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Success!")
                            .setContentText(it.splitTransactionResponse?.msg)
                            .setConfirmClickListener {
                                it.cancel()
                                viewModel.clearData()
                                (activity as SplitTransactionActivity?)?.setTab(0)
                                (activity as SplitTransactionActivity?)?.viewModel?.mAction?.value =
                                    JlgAction(JlgAction.JLG_CLEAR_DATA)
                            }
                            .show()
                    }

                    JlgAction.CLICK_SEARCH_ACCCOUNT_NUMBER->
                    {
                        var intent: Intent = Intent(activity, SearchActivity::class.java)
                        startActivityForResult(intent, Constants.INTENT_SEARCH_ACCOUNT_FROM_JLG_SPLIT)
                    }

                }


            })



        (activity as SplitTransactionActivity?)?.viewModel?.accountsLiveData?.observe(
            viewLifecycleOwner,
            Observer {
                (activity as SplitTransactionActivity?)?.viewModel?.cancelLoading()
                viewModel.setAccountList(   (activity as SplitTransactionActivity?)?.viewModel?.accountsData)
                viewModel.totalAmount.set((activity as SplitTransactionActivity?)?.viewModel?.totalAmount?.get())
                //(activity as SplitTransactionActivity?)?.viewModel?.setAccountData(it)

            })

        viewModel.mAction.observe(viewLifecycleOwner, Observer {
            when (it.action) {
                JlgAction.ADD_CHARGES -> {
                    it.charges?.let { it1 -> chargesAdapter.addCharge(it1) }
                    viewModel.setSelectedAccountNumber(0)
                    viewModel.setSelectedCharges(0)
                    viewModel.amountSelected.set("")
                    viewModel.accountSelected.set("")
                    viewModel.chargeSelected.set("")
                }
                JlgAction.CLICK_SUBMIT_FROM_JLG_SPLIT_OTHER_DETAILS -> {

                    (activity as SplitTransactionActivity?)?.viewModel?.particulars?.set(viewModel.particulars.get())
                    (activity as SplitTransactionActivity?)?.viewModel?.instrumentType?.set(
                        viewModel.instrumenTypeSelected.get()
                    )
                    (activity as SplitTransactionActivity?)?.viewModel?.instrumentType?.set(
                        viewModel.instrumenTypeSelected.get()
                    )
                    (activity as SplitTransactionActivity?)?.viewModel?.instrumentNumber?.set(
                        viewModel.instrumentNumber.get()
                    )
                    (activity as SplitTransactionActivity?)?.viewModel?.instrumentDate?.set(
                        viewModel.submitInstrumentDate.get()
                    )
                    (activity as SplitTransactionActivity?)?.viewModel?.totalAmount?.set(viewModel.totalAmount.get())

                    //(activity as SplitTransactionActivity?)?.viewModel?.initLoading(context)

                    (activity as SplitTransactionActivity?)?.viewModel?.callSpitTransactionApi(
                        context
                    )
                }

                JlgAction.CLICK_PREVIOUS_FROM_OTHER_DETAILS -> {
                    (activity as SplitTransactionActivity?)?.gotoPrevious()
                }
            }
        })

    }

    private fun setRecyclerView(rvChargeList: RecyclerView) {

        rvChargeList.setHasFixedSize(true)
        rvChargeList.layoutManager = LinearLayoutManager(context)
        chargesAdapter = ChargesAdapter()
        rvChargeList.adapter = chargesAdapter
        setObservable(chargesAdapter)

    }

    private fun setObservable(chargesAdapter: ChargesAdapter) {
        chargesAdapter.chargeListLiveData.observe(viewLifecycleOwner, Observer {
            if (it.size > 0) {
                viewModel.chargeListVisibility.set(View.VISIBLE)
                (activity as SplitTransactionActivity?)?.viewModel?.setChargesData(it)
            } else {
                viewModel.chargeListVisibility.set(View.GONE)
            }
        })


        chargesAdapter.mAction.observe(viewLifecycleOwner, Observer {
            when (it.action) {
                JlgAction.CLICK_DELETE -> {
                    it.position?.let { it1 ->
                        chargesAdapter.removeItem(it1)
                        chargesAdapter.notifyDataSetChanged()

                    }
                }
            }
        })
    }


}