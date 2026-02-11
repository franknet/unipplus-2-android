package com.jfpsolucoes.unipplus2.core.database

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

object UPFirebaseDatabase {
    private var dbRef: DatabaseReference? = null

    val userProfile = MutableStateFlow<UPUserProfileEntity?>(null)

    fun initialize() {
        val db = FirebaseDatabase.getInstance().apply {
            setPersistenceEnabled(true)
        }
        dbRef = db.reference.apply {
            keepSynced(true)
        }
    }

    fun startListeningUser(
        context: CoroutineContext = Dispatchers.IO,
        userRg: String
    )  {
        val userRef = dbRef?.child("users")?.child(userRg)
        userRef?.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                CoroutineScope(context).launch {
                    userProfile.emit(snapshot.getValue(UPUserProfileEntity::class.java))
                }
            }
            override fun onCancelled(error: DatabaseError) { }
        })
    }
}