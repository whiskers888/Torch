package com.example.telegram.utilits

enum class AppStates(val state:String) {
    ONLINE("В сети"),
    OFFLINE("Не в сети"),
    TYPING("печатает...");

    companion object{
        fun updateStates(appStates: AppStates){
            if(AUTH.currentUser != null){
                REF_DATABASE_ROOT.child(NODE_USERS).child(CURRENT_UID).child(CHILD_STATE)
                    .setValue(appStates.state)
                    .addOnSuccessListener { USER.state = appStates.state }
                    .addOnFailureListener{showToast(it.message.toString())}
            }

        }
    }
}