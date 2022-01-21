package com.finwin.doorstep.gramadhanasree.home.customer_creation.action

import com.finwin.doorstep.gramadhanasree.home.customer_creation.pojo.*

class CustomerCretaionAction {
    companion object {

        public const val DEFAULT: Int = -1
        public const val CLICK_PROFILE_IMAGE: Int = 1
        public const val CLICK_SIGNATURE: Int = 2
        public const val CLICK_ID_PROOF_ONE: Int = 3
        public const val CLICK_ID_PROOF_TWO: Int = 4
        public const val CLICK_LOGOUT: Int = 5
        public const val CLICK_JLG_LOAN: Int = 6
        public const val CLICK_CUSTOMER_CREATION: Int = 7
        public const val API_ERROR: Int = 8
        public const val GET_REF_CODE_SUCCESS: Int = 9
        public const val GET_POST_OFFICE_SUCCESS: Int = 10
        public const val CUSTOMER_CREATION_SUCCESS: Int = 11
        public const val SENT_OTP_SUCCESS: Int = 12
        public const val VALIDATE_OTP_SUCCESS: Int = 13
        public const val POST_OFFICE_ERROR: Int = 14

    }
    var action: Int = 0
    lateinit var error: String
    lateinit var referenceCodesResponse: ReferenceCodesResponse
    lateinit var postOfficeResponse: getPostOfficeResponse
    lateinit var customerCreationResponse: CustomerCreationResponse
    lateinit var validateOtpResponse: ValidateOtpResponse
    lateinit var createOtpResponse: CreateOtpResponse



    constructor(action: Int) {
        this.action = action
    }

    constructor(action: Int, referenceCodesResponse: ReferenceCodesResponse) {
        this.action = action
        this.referenceCodesResponse = referenceCodesResponse
    }

    constructor(action: Int, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int, postOfficeResponse: getPostOfficeResponse) {
        this.action = action
        this.postOfficeResponse = postOfficeResponse
    }

    constructor(action: Int, customerCreationResponse: CustomerCreationResponse) {
        this.action = action
        this.customerCreationResponse = customerCreationResponse
    }


    constructor(action: Int, createOtpResponse: CreateOtpResponse) {
        this.action = action
        this.createOtpResponse = createOtpResponse
    }

    constructor(action: Int, validateOtpResponse: ValidateOtpResponse) {
        this.action = action
        this.validateOtpResponse = validateOtpResponse
    }


}