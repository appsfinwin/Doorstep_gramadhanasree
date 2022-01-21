package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_creation_pager_adapter

import android.content.Context
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.general_details.JlgLoanGeneralDetailsFragment
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.jlg_loan_details.JlgLoanDetailsFragment
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.JlgLoanSelectGroupFragment
import com.finwin.doorstep.gramadhanasree.R

class JlgPagerAdapter(context: Context,
                      fm: FragmentManager
) : FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
       return 3
    }
    val generalDetailsFragment: JlgLoanGeneralDetailsFragment = JlgLoanGeneralDetailsFragment.newInstance()
    val selectGroupFragment: JlgLoanSelectGroupFragment = JlgLoanSelectGroupFragment.newInstance()
    val jlgLoanDetailsFragment: JlgLoanDetailsFragment = JlgLoanDetailsFragment.newInstance()

    val context: Context= context
    override fun getItem(position: Int): Fragment {
        var  fragment: Fragment= Fragment()
        //status_Completed= sharedPreferences.getInt(Constants.JLG_FRAGMENT_STATUS, 1);
        when(position)
        {
            0->{fragment=  generalDetailsFragment}
            1->{fragment=  selectGroupFragment}
            2->{fragment=  jlgLoanDetailsFragment}
        }
        return  fragment
    }
    @StringRes
    val TAB_TITLES = intArrayOf(
        R.string.general_details,
        R.string.select_group,
        R.string.loan_details

    )

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES.get(position))
    }
}