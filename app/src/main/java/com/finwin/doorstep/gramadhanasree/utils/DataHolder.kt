package com.finwin.doorstep.gramadhanasree.utils



import com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo.Dat
import com.finwin.doorstep.gramadhanasree.login.pojo.getMasters.Data
import java.util.*

class DataHolder {
    companion object
    {
        var nameTitleList: List<Data> = ArrayList<Data>()
        var genderList: List<Data> = ArrayList<Data>()
        var maritalStatusList: List<Data> = ArrayList<Data>()
        var occupationList: List<Data> = ArrayList<Data>()
        var accountType: List<Data> = ArrayList<Data>()
        var modeOperation: List<Data> = ArrayList<Data>()
        var constitution: List<Data> = ArrayList<Data>()
        var branchdetails: List<Data> = ArrayList<Data>()
        var accountCtg: List<Data> = ArrayList<Data>()
        var accountRelation: List<Data> = ArrayList<Data>()
        var dat: List<Dat> = ArrayList<Dat>()
    }

}