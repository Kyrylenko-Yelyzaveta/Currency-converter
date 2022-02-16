package com.example.currencyconverter.model.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.currencyconverter.model.entity.Item
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class FirebaseWorker : IFirebaseWorker {
    private var database: DatabaseReference = Firebase.database.reference
    private val _currencyToUSD = MutableLiveData<ArrayList<Item>>()
    val currencyToUSD: LiveData<ArrayList<Item>> = _currencyToUSD


    override fun readData() {
        val itemsListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val dataMap = dataSnapshot.getValue<HashMap<String,ArrayList<Double>>>()

                var itemsArray = ArrayList<Item>()
                if (dataMap != null) {
                    for (i in dataMap) {
                        itemsArray.add(Item(i.key,i.value))
                    }
                    _currencyToUSD.postValue(itemsArray)
                    Log.d("Mike", itemsArray.toString())
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {}
        }
        database.addValueEventListener(itemsListener)

    }
    init {
        readData()
    }
}