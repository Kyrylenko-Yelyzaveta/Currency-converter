package com.example.currencyconverter.model.entity

import com.google.firebase.database.Exclude

data class Item(val name: String, var values: ArrayList<Double>) {

}