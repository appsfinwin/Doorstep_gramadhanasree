package com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.adapter

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.databinding.*
import androidx.lifecycle.MutableLiveData
import com.finwin.doorstep.gramadhanasree.BR
import com.finwin.doorstep.gramadhanasree.databinding.LayoutRowGroupBinding
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.room.Member
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.action.SelectGroupAction
import com.finwin.doorstep.gramadhanasree.home.jlg.jlg_loan_creation.select_group.pojo.ProductData
import java.lang.Exception
import java.util.*

class RowGroupViewModel(
    val member: List<Member>,
    val position: Int,
    val optionValue: Boolean,
    val binding: LayoutRowGroupBinding,
    val productData: ObservableArrayList<ProductData>,
    var mAction: MutableLiveData<SelectGroupAction>,
    var memberListLiveData: MutableLiveData<List<Member>>
) : BaseObservable() {


    var slNumber: ObservableField<String> = ObservableField(member[position].slno)
    var memberName: ObservableField<String> = ObservableField(member[position].customerName)
    var customerId: ObservableField<String> = ObservableField(member[position].customerId)
    var accountNumber: ObservableField<String> = ObservableField("")
    var amount: ObservableField<String> = ObservableField(member[position].amount)
    var nomineeName: ObservableField<String> = ObservableField("")
    var nomineeMobile: ObservableField<String> = ObservableField("")
    var nomineeAddress: ObservableField<String> = ObservableField("")
    var insuranceFee: ObservableField<String> = ObservableField(member[position].insuranceFee)
    var documentationFee: ObservableField<String> =
        ObservableField(member[position].documentationFee)
    var cgst: ObservableField<String> = ObservableField(member[position].cgst)
    var sgst: ObservableField<String> = ObservableField(member[position].sgst)
    var cess: ObservableField<String> = ObservableField(member[position].cess)
    var disbursementAmount: ObservableField<String> =
        ObservableField(member[position].disbursementAmount)
    var isOptionDisable: ObservableField<Boolean> = ObservableField(optionValue)
    //var isOptionDisable:Boolean = optionValue

    var registry: PropertyChangeRegistry = PropertyChangeRegistry()
    var selectedOption = 0
    var productId: ObservableField<String> = ObservableField("")
    var productList: ObservableArrayList<String> = ObservableArrayList()
    var productListListData: ObservableArrayList<ProductData> = ObservableArrayList()
    private var selectedProduct = 0
    var timer = Timer()

    @JvmName("getSelectedProduct1")
    @Bindable
    fun getSelectedProduct(): Int {
        return selectedProduct
    }

    @JvmName("setSelectedProduct1")
    fun setSelectedProduct(selectedProduct: Int) {
        this.selectedProduct = selectedProduct
        registry.notifyChange(this, BR.selectedProduct1)
    }

    init {
        productList.clear()
        productListListData.clear()
        productList.add("--select customer goods--")
        productListListData.add(ProductData("--select customer goods--", "0", "0"))

        for (i in productData.indices) {
            productList.add(productData[i].productName)
            productListListData.add(productData[i])
        }


    }


    var selectedOptions = binding.spinner21.setOnItemSelectedListener(object :
        OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View, pos: Int, id: Long) {
            if (pos != 0) {

            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {}
    })

    fun clickMemberDelete(view: View) {

    }

    fun clickSave(view: View) {

        disbursementAmount.set(

            ((amount.get()?.toDouble())?.minus(
                (
                        (insuranceFee.get()?.toDouble())!!
                                + (documentationFee.get()?.toDouble()!!)
                                + (cgst.get()?.toDouble()!!)
                                + (sgst.get()?.toDouble()!!)
                                + (cess.get()?.toDouble()!!))
            )
                    ).toString()

        )

        var member = Member(
            slno = slNumber.get().toString(),
            address = nomineeAddress.get().toString(),
            amount.get().toString(),
            cgst.get().toString(),
            cess.get().toString(),
            coApplicant = nomineeName.get().toString(),
            productId.get().toString(),
            customerId.get().toString(),
            memberName.get().toString(),
            disbursementAmount.get().toString(),
            documentationFee.get().toString(),
            insuranceFee.get().toString(),
            mobile = nomineeMobile.get().toString(),
            sgst.get().toString()
        )
        mAction.value = (SelectGroupAction(SelectGroupAction.CHANGE_DATA, member))

    }

    fun onInsuranceFeeChanged(text: CharSequence?) {
        if (text?.length!! >0) {

            var _disbursementAmount= (if (amount.get() == "") "0" else amount.get())?.toDouble()?.minus(
                (
                        ((if (text.toString() == "") "0" else text.toString())?.toDouble())!!
                                + ((if (documentationFee.get() == "") "0" else documentationFee.get())?.toDouble()!!)
                                + ((if (cgst.get() == "") "0" else cgst.get())?.toDouble()!!)
                                + ((if (sgst.get() == "") "0" else sgst.get())?.toDouble()!!)
                                + ((if (cess.get() == "") "0" else cess.get())?.toDouble()!!))
            )

            disbursementAmount.set(_disbursementAmount.toString())

            member[position].disbursementAmount = disbursementAmount.get().toString()
            memberListLiveData.value = member
        }
    }

    fun onDocumentaionChanged(text: CharSequence?) {
        if (text?.length!! >0) {

            var _disbursementAmount= (if (amount.get() == "") "0" else amount.get())?.toDouble()?.minus(
                (
                        ((if (insuranceFee.get() == "") "0" else insuranceFee.get())?.toDouble())!!
                                + ((if (text.toString() == "") "0" else text.toString())?.toDouble()!!)
                                + ((if (cgst.get() == "") "0" else cgst.get())?.toDouble()!!)
                                + ((if (sgst.get() == "") "0" else sgst.get())?.toDouble()!!)
                                + ((if (cess.get() == "") "0" else cess.get())?.toDouble()!!))
            )

            disbursementAmount.set(_disbursementAmount.toString())

            member[position].disbursementAmount = disbursementAmount.get().toString()
            memberListLiveData.value = member
        }
    }

    fun onCgstChanged(text: CharSequence?) {
        if (text?.length!! >0) {

            var _disbursementAmount= (if (amount.get() == "") "0" else amount.get())?.toDouble()?.minus(
                (
                        ((if (insuranceFee.get() == "") "0" else insuranceFee.get())?.toDouble())!!
                                + ((if (documentationFee.get() == "") "0" else documentationFee.get())?.toDouble()!!)
                                + ((if (text.toString() == "") "0" else text.toString())?.toDouble()!!)
                                + ((if (sgst.get() == "") "0" else sgst.get())?.toDouble()!!)
                                + ((if (cess.get() == "") "0" else cess.get())?.toDouble()!!))
            )

            disbursementAmount.set(_disbursementAmount.toString())

            member[position].disbursementAmount = disbursementAmount.get().toString()
            memberListLiveData.value = member
        }
    }

    fun onSgstChanged(text: CharSequence?) {
        if (text?.length!! >0) {

            var _disbursementAmount= (if (amount.get() == "") "0" else amount.get())?.toDouble()?.minus(
                (
                        ((if (insuranceFee.get() == "") "0" else insuranceFee.get())?.toDouble())!!
                                + ((if (documentationFee.get() == "") "0" else documentationFee.get())?.toDouble()!!)
                                + ((if (cgst.get() == "") "0" else cgst.get())?.toDouble()!!)
                                + ((if (text.toString() == "") "0" else text.toString())?.toDouble()!!)
                                + ((if (cess.get() == "") "0" else cess.get())?.toDouble()!!))
            )

            disbursementAmount.set(_disbursementAmount.toString())

            member[position].disbursementAmount = disbursementAmount.get().toString()
            memberListLiveData.value = member
        }
    }

    fun onCessChanged(text: CharSequence?) {
        if (text?.length!! >0) {
            var _disbursementAmount= (if (amount.get() == "") "0" else amount.get())?.toDouble()?.minus(
                (
                        ((if (insuranceFee.get() == "") "0" else insuranceFee.get())?.toDouble())!!
                                + ((if (documentationFee.get() == "") "0" else documentationFee.get())?.toDouble()!!)
                                + ((if (cgst.get() == "") "0" else cgst.get())?.toDouble()!!)
                                + ((if (sgst.get() == "") "0" else sgst.get())?.toDouble()!!)
                                + ((if (text.toString() == "") "0" else text.toString())?.toDouble()!!))
            )

            disbursementAmount.set(_disbursementAmount.toString())

            member[position].disbursementAmount = disbursementAmount.get().toString()
            memberListLiveData.value = member
        }
    }

    fun onAmountChanged(text: CharSequence?) {
        if (text?.length!! >0) {
            try {

                var _disbursementAmount= (if (text == "") "0" else text.toString()).toDouble()?.minus(
                    (
                            ((if (insuranceFee.get() == "") "0" else insuranceFee.get())?.toDouble())!!
                                    + ((if (documentationFee.get() == "") "0" else documentationFee.get())?.toDouble()!!)
                                    + ((if (cgst.get() == "") "0" else cgst.get())?.toDouble()!!)
                                    + ((if (sgst.get() == "") "0" else sgst.get())?.toDouble()!!)
                                    + ((if (cess.get() == "") "0" else cess.get())?.toDouble()!!))
                )

                disbursementAmount.set(_disbursementAmount.toString())
                member[position].disbursementAmount = disbursementAmount.get().toString()
                memberListLiveData.value = member
            }catch (e:Exception)
            {
                e.stackTrace
            }
        }
    }

}