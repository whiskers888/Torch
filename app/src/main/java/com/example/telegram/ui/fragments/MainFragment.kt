package com.example.telegram.ui.fragments

import androidx.fragment.app.Fragment
import com.example.telegram.R
import com.example.telegram.utilits.APP_ACTIVITY
import hideKeyboard


// Главный фрагмент где содержатся все чаты, группы и каналы с которыми взаимодействует user

class MainFragment : Fragment(R.layout.fragment_chats) {

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Torch"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
    }
}
