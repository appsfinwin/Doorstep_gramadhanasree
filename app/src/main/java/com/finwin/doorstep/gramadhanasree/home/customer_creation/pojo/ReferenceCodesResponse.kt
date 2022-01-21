package com.finwin.doorstep.gramadhanasree.home.customer_creation.pojo

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ReferenceCodesResponse(
    val `data`: Data
)

data class Data(
    @SerializedName("Table")
    @Expose
    val prefix: List<Prefix>,

    @SerializedName("Table1")
    @Expose
    val branch: List<Branch>,

    @SerializedName("Table2")
    @Expose
    val gender: List<Gender>,

    @SerializedName("Table3")
    @Expose
    val idProof: List<IdProof>,

    @SerializedName("Table4")
    @Expose
    val addressType: List<AddressType>,

    @SerializedName("Table5")
    @Expose
    val location: List<Location>,

    @SerializedName("Table6")
    @Expose
    val phoneType: List<PhoneType>
)

data class Prefix(
    val Code: String,
    val Name: String
)

data class Branch(
    val Branch_Code: String,
    val Branch_Name: String
)

data class Gender(
    val Code: String,
    val Name: String
)

data class IdProof(
    val Code: String,
    val Name: String
)

data class AddressType(
    val Code: String,
    val Name: String
)

data class Location(
    val Code: String,
    val Name: String
)

data class PhoneType(
    val Code: String,
    val Name: String
)