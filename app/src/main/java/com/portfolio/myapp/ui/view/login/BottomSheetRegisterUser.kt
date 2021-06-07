package com.portfolio.myapp.ui.view.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.user.UserModel
import kotlinx.android.synthetic.main.sheet_register_user.*
import kotlinx.android.synthetic.main.sheet_register_user.view.*

class BottomSheetRegisterUser(var itemClickListener: RegisterClickListener):
    BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.sheet_register_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        btnRegisterUser.setOnClickListener {
            val user= UserModel(
                view.txtUserMailRegister.text.toString(),
                view.txtUserNameRegister.text.toString(),
                view.txtUserLastNameRegister.text.toString(),
                view.txtUserPasswordRegister.text.toString()
            )
            Logger.i(Gson().toJson(user))
            itemClickListener.onRegisterClickListener(user)
        }
    }

    interface RegisterClickListener {
        fun onRegisterClickListener(userModel: UserModel)
    }
}