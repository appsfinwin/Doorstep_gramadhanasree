package com.finwin.doorstep.gramadhanasree.login.action

import com.finwin.doorstep.gramadhanasree.login.pojo.LoginResponse
import com.finwin.doorstep.gramadhanasree.login.pojo.getMasters.GetMasters

class LoginAction {

    companion object{
        public var DEFAULT: Int = -1;
        public var API_ERROR: Int = 1;
        public var LOGIN_SUCCESS: Int = 2;
        public var LOGIN_ERROR: Int = 3;
        public var GET_MASTERS_SUCCESS: Int = 4;

    }
    lateinit var error :String
    var action: Int? = null
    lateinit var loginResponse: LoginResponse
    lateinit var getMasters: GetMasters



    constructor( action: Int?,error: String) {
        this.error = error
        this.action = action
    }

    constructor(action: Int?, loginResponse: LoginResponse) {
        this.action = action
        this.loginResponse = loginResponse
    }

    constructor(action: Int?) {
        this.action = action
    }

    constructor(action: Int?, getMasters: GetMasters) {
        this.action = action
        this.getMasters = getMasters
    }


}