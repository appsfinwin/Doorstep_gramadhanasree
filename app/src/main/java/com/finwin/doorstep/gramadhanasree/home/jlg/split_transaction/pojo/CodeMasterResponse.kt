package com.finwin.doorstep.gramadhanasree.home.jlg.split_transaction.pojo

import com.google.gson.annotations.SerializedName

data class CodeMasterResponse(
    val Charges: List<Charge>,
    val CollectionStaff: List<CollectionStaff>,
    val InstrumentType: List<InstrumentType>,
    val Mode: List<Mode>,
    val Period: List<Period>,
    val Scheme: List<Scheme>,
    val Sector: List<Sector>,
    val SubTranType: List<SubTranType>,

    @SerializedName("Subsector")
    val subSector: List<SubSector>,
    val status: String
)

data class Charge(
    val Display_Name: String,
    val Gl_Code: String
)

data class CollectionStaff(
    val Cust_ID: String,
    val Name: String
)

data class InstrumentType(
    val Code: String,
    val Name: String,
    val Type_Code: String
)

data class Mode(
    val Code: String,
    val Name: String
)

data class Period(
    val Code: String,
    val Name: String
)

data class Scheme(
    val Lnp_EMIType: String,
    val Lnp_IntCalcType: String,
    val Lnp_IsSplitAcc: String,
    val Sch_Code: String,
    val Sch_Name: String
)

data class Sector(
    val Code: String,
    val Name: String,
    val Type_Code: String
)

data class SubTranType(
    val Code: String,
    val Name: String
)

data class SubSector(
    val Code: String,
    val Name: String,
    val Type_Code: String
)