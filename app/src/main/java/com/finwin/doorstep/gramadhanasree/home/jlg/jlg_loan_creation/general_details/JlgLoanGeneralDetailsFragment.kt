package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.general_details

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.JlgLoanCreationActivity
import com.finwin.doorstep.gramadhanasree.R
import com.finwin.doorstep.gramadhanasree.databinding.JlgLoanGeneralDetailsFragmentBinding
import com.finwin.doorstep.gramadhanasree.utils.Constants

class JlgLoanGeneralDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = JlgLoanGeneralDetailsFragment()
    }

    private lateinit var viewModel: JlgLoanGeneralDetailsViewModel
    private lateinit var binding: JlgLoanGeneralDetailsFragmentBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.jlg_loan_general_details_fragment,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(JlgLoanGeneralDetailsViewModel::class.java)
        binding.viewModel = viewModel
//        viewModel.initLoading(view?.context)
//        viewModel.getCodeMasters()

        sharedPreferences =
            requireActivity().getSharedPreferences(Constants.PREFERENCE_NAME, MODE_PRIVATE)
        editor = sharedPreferences?.edit()!!
        editor.putString(Constants.JLG_SCHEME_CODE, "")
        editor.apply()

        (activity as JlgLoanCreationActivity).viewModel.mAction.observe(
            viewLifecycleOwner,
            Observer {

                (activity as JlgLoanCreationActivity).viewModel.cancelLoading()
                when (it.action) {
                    JlgLoanCreationAction.CODE_MASTERS_SUCCESS -> {
                        it.codeMasterResponse?.let { it1 ->


                            viewModel.setSchemeData(it1.Scheme)
                            viewModel.setSectorData(it1.Sector)
                            viewModel.setSubSectorData(it1.subSector)
                        }

                    }
                    JlgLoanCreationAction.JLG_CLEAR_DATA -> {
                        viewModel.clearData()
                    }

                }
            })

        viewModel.mAction.observe(viewLifecycleOwner, Observer {

            viewModel.cancelLoading()
            when (it.action) {
                JlgLoanGeneralDetailsAction.CLICK_NEXT -> {

                    (activity as JlgLoanCreationActivity?)?.viewModel?.applicationNumber?.set(
                        viewModel.etAppliactionNumber.get()
                    )
                    (activity as JlgLoanCreationActivity?)?.viewModel?.schemeCode?.set(viewModel.schemeCode.get())
                    (activity as JlgLoanCreationActivity?)?.viewModel?.sectorCode?.set(viewModel.sectorCode.get())
                    (activity as JlgLoanCreationActivity?)?.viewModel?.subSectorCode?.set(viewModel.subSectorCode.get())
                    (activity as JlgLoanCreationActivity?)?.viewModel?.remark?.set(viewModel.etRemark.get())

                    (activity as JlgLoanCreationActivity?)?.gotoNext()
                }
                JlgLoanGeneralDetailsAction.SELECT_SCHEME -> {
                    (activity as JlgLoanCreationActivity).viewModel.mSchemeCode.value =
                        it.selectedScheme.Sch_Code
                    (activity as JlgLoanCreationActivity).viewModel.selectedScheme.set(it.selectedScheme)
                }
            }
        })

        viewModel.mSchemeCode.observe(viewLifecycleOwner, Observer {

        })


    }

}