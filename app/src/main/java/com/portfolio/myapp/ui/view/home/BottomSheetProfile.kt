package com.portfolio.myapp.ui.view.home

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.R
import com.portfolio.myapp.data.model.user.UserModel
import kotlinx.android.synthetic.main.sheet_profile_user.*
import kotlinx.android.synthetic.main.sheet_register_user.*
import kotlinx.android.synthetic.main.sheet_register_user.view.*

class BottomSheetProfile(userModel: UserModel,var itemClickListener: ProfileClickListener) : BottomSheetDialogFragment() {

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            return inflater.inflate(R.layout.sheet_profile_user, container, false)
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            btnEditProfile.setOnClickListener {
                itemClickListener.onChangeProfileClickListener()
            }
            txtChangePass.setOnClickListener {
                itemClickListener.onChangePasswordClickListener()
            }

        }

        interface ProfileClickListener {
            fun onChangePasswordClickListener()
            fun onChangeProfileClickListener()
        }
}