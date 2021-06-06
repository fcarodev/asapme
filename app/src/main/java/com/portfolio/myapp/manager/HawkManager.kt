package com.portfolio.myapp.manager

import com.orhanobut.hawk.Hawk
import com.portfolio.myapp.constant.USER
import com.portfolio.myapp.model.User

class HawkManager {

    fun getUserLoggedIn():User{
        if(Hawk.contains(USER)){
            return Hawk.get(USER)
        }else  {
            return User("0","sample@sample.com","sample name","sample lastname")
        }
    }
}