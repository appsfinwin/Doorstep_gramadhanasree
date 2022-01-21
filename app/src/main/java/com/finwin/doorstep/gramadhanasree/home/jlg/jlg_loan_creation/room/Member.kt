package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "member_table")
class Member(

    @PrimaryKey
    @ColumnInfo(name = "Slno")
    val slno: String,

    @ColumnInfo(name = "Address")
    val address: String,

    @ColumnInfo(name = "Amount")
    val amount: String,

    @ColumnInfo(name = "CGST")
    val cgst: String,

    @ColumnInfo(name = "Cess")
    val cess: String,

    @ColumnInfo(name = "CoApplicant")
    val coApplicant: String,

    @ColumnInfo(name = "ConsumerGoods")
    val consumerGoods: String,

    @ColumnInfo(name = "CustID")
    val customerId: String,

    @ColumnInfo(name = "Customer Name")
    val customerName: String,

    @ColumnInfo(name = "Disbursement Amount")
    var disbursementAmount: String,

    @ColumnInfo(name = "Documentation Fee")
    val documentationFee: String,

    @ColumnInfo(name = "Insurance Fee")
    val insuranceFee: String,

    @ColumnInfo(name = "Mobile")
    val mobile: String,

    @ColumnInfo(name = "SGST")
    val sgst: String

)