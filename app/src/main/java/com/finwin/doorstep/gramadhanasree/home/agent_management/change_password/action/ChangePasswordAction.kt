package com.finwin.doorstep.gramadhanasree.home.agent_management.change_password.action

import com.finwin.doorstep.gramadhanasree.home.agent_management.change_password.pojo.ChangePasswordResponse

class ChangePasswordAction {
    companion object{
        public var DEFAULT: Int = -1;
        public var API_ERROR: Int = 1;
        public var CHANGE_PASSWORD_SUCCESS: Int = 5;

    }
    var action: Int? = null
    lateinit var error:String
    lateinit var changePasswordResponse: ChangePasswordResponse

    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, error: String) {
        this.action = action
        this.error = error
    }

    constructor(action: Int?, changePasswordResponse: ChangePasswordResponse) {
        this.action = action
        this.changePasswordResponse = changePasswordResponse
    }
}