package com.jfpsolucoes.unipplus2.core.database

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.snapshots
import com.jfpsolucoes.unipplus2.core.database.entities.UPUserProfileEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

object UPFirebaseDatabase {
    private val _credentialsDao = EncryptedDataBase.shared.credentialsDao()
    private lateinit var dbRef: DatabaseReference
    private var _initialized = false

    val userProfile = MutableStateFlow<UPUserProfileEntity?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    fun initialize() {
        val db = FirebaseDatabase.getInstance().apply {
            setPersistenceEnabled(true)
        }
        dbRef = db.reference.apply {
            keepSynced(true)
        }
        CoroutineScope(Dispatchers.IO).launch {
            _credentialsDao.get()
                .filterNotNull()
                .filter({ !_initialized })
                .flatMapLatest { credentials ->
                    dbRef.child("users").child(credentials.rg).snapshots
                }
                .map { snapshot ->
                    snapshot.getValue(UPUserProfileEntity::class.java)
                }
                .collect {
                    userProfile.value = it
                }
        }
    }
}