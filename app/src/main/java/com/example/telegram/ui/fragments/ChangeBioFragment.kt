package com.example.telegram.ui.fragments

import com.example.telegram.R
import com.example.telegram.database.*
import kotlinx.android.synthetic.main.fragment_change_bio.*
import showToast

//Фрагмент для изменения биографии пользователя
class ChangeBioFragment : BaseChangeFragment(R.layout.fragment_change_bio) {

    override fun onResume() {
        super.onResume()
        settings_input_bio.setText(USER.bio)
    }

    override fun change() {
        super.change()
        val newBio = settings_input_bio.text.toString()
        setBioToDatabase(newBio)

    }


}