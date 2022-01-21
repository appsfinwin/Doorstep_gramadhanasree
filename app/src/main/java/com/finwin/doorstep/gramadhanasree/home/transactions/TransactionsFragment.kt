package com.finwin.doorstep.gramadhanasree.home.transactions

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.FragmentTransactionsBinding
import com.finwin.doorstep.gramadhanasree.home.home_activity.HomeActivity
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_deposit.CashDepositFragment
import com.finwin.doorstep.gramadhanasree.home.transactions.cash_withdrawal.CashWithdrawalFragment
import com.finwin.doorstep.gramadhanasree.home.transactions.loan_collection.LoanCollectionFragment

class TransactionsFragment : Fragment() {

    lateinit var binding: FragmentTransactionsBinding
    lateinit var viewmodel: TransactionsViewmodel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Transactions"
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_transactions, container, false)
        viewmodel = ViewModelProvider(this).get(TransactionsViewmodel::class.java)
        binding.viewmodel = viewmodel
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            TransactionsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Transactions"
        viewmodel.mAction.observe(viewLifecycleOwner, Observer {

            when (it.action) {
                TransactionAction.CLICK_CASH_DEPOSIT -> {

                    val myFragment: Fragment = CashDepositFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }
                TransactionAction.CLICK_CASH_WITHDRAWAL -> {
                    val myFragment: Fragment = CashWithdrawalFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.add(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }
                TransactionAction.CLICK_TRANSFER -> {
                    val myFragment: Fragment = CashDepositFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                        ?.replace(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }

                TransactionAction.CLICK_LOAN_COLLECTION -> {
                    val myFragment: Fragment = LoanCollectionFragment()
                    activity?.supportFragmentManager?.beginTransaction()
                            ?.replace(R.id.frame_layout, myFragment)?.addToBackStack(null)?.commit()
                }
            }

        })
    }

    override fun onResume() {
        super.onResume()
        HomeActivity.activityHomeBinding.appBar.tvHeading.text = "Transactions"
    }

    override fun onStop() {
        super.onStop()
        viewmodel.mAction.value= TransactionAction(TransactionAction.DEFAULT)
    }
}