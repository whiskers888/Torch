package com.example.telegram.ui.screens.main_list

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.R
import com.example.telegram.database.*
import com.example.telegram.models.CommonModel
import com.example.telegram.utilits.APP_ACTIVITY
import com.example.telegram.utilits.AppValueEventListener
import hideKeyboard
import kotlinx.android.synthetic.main.fragment_main_list.*


// Главный фрагмент где содержатся все чаты, группы и каналы с которыми взаимодействует user

class MainListFragment : Fragment(R.layout.fragment_main_list) {

    private lateinit var mRecyclerView:RecyclerView
    private lateinit var mAdapter: MainListAdapter
    private val mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private val mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private val mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()

    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Torch"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_list_recycler_view
        mAdapter = MainListAdapter()

        // 1 Запрос
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener{dataSnapshot ->
        mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mListItems.forEach {model ->

                // 2 запрос
                mRefUsers.child(model.id).addListenerForSingleValueEvent(AppValueEventListener{dataSnapshot1 ->
                    val newModel = dataSnapshot1.getCommonModel()

                    // 3 запрос
                    mRefMessages.child(model.id).limitToLast(1)
                        .addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot2 ->

                            val tempList = dataSnapshot2.children.map {it.getCommonModel()}

                            if (tempList.isEmpty()){
                                newModel.lastMessage = "Чат очищен"
                            }else {
                                newModel.lastMessage = tempList[0].text
                            }
                            if (newModel.fullname.isEmpty()){
                                newModel.fullname = newModel.phone
                            }
                            mAdapter.updateListItems(newModel)
                    })
                })
            }
        })

        mRecyclerView.adapter = mAdapter
    }
}
