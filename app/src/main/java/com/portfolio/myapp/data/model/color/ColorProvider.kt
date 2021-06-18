package com.portfolio.myapp.data.model.color

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.portfolio.myapp.data.model.sprint.SprintModel

class ColorProvider {
    val db = FirebaseFirestore.getInstance()

    fun getColors(): LiveData<MutableList<ColorModel>>{
        val mutableData = MutableLiveData<MutableList<ColorModel>>()
        db.collection("color")
            .get()
            .addOnSuccessListener { resultSprints ->
                var colorModel = mutableListOf<ColorModel>()
                colorModel = resultSprints.toObjects(ColorModel::class.java)
                mutableData.value = colorModel
            }

        return mutableData
    }
}