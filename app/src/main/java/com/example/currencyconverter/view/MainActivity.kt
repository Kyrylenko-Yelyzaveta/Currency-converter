package com.example.currencyconverter.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.model.data.DefaultData
import com.example.currencyconverter.model.network.FirebaseWorker
import com.example.currencyconverter.model.entity.Item
import com.example.currencyconverter.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    companion object {
        const val CRYPTO_NAME_KEY = "CRYPTO_NAME_KEY"
        const val CHARTS_NAME_KEY = "CHARTS_NAME_KEY"
        var cryptoName = ""
        var chartName = ""
    }

    private lateinit var binding: ActivityMainBinding
    private val adapter = ItemAdapter()
    private  var list: MutableList<Item>  = mutableListOf()
    private  var firebaseWorker = FirebaseWorker()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
        initObserver()




    }
    override fun onSaveInstanceState(outState: Bundle) { // Here You have to save count value
        super.onSaveInstanceState(outState)
        outState.putString(CRYPTO_NAME_KEY, cryptoName)
        outState.putString(CHARTS_NAME_KEY, chartName)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) { // Here You have to restore count value
        super.onRestoreInstanceState(savedInstanceState)
        cryptoName = savedInstanceState.getString(CRYPTO_NAME_KEY,"")
        chartName = savedInstanceState.getString(CHARTS_NAME_KEY, "")
    }


    private fun init()
    {
        binding.apply {
            recView.layoutManager = LinearLayoutManager(this@MainActivity)
            recView.adapter = adapter
            list.add(Item("BTC|USD", DefaultData.btcToUsd))
            list.add(Item("ETH|USD", DefaultData.ethToUsd))
            list.add(Item("LTC|USD", DefaultData.ltcToUsd))
            list.add(Item("XRP|USD", DefaultData.xrpToUsd))
            list.add(Item("DOGE|USD", DefaultData.dogeToUsd))
            adapter.showItems(list)

        }
    }
    private fun initObserver()
    {
        firebaseWorker.currencyToUSD.observe(this){ data ->
            if(data!=null)
            {
                adapter.showItems(data)
                adapter.notifyDataSetChanged()

            }
        }

    }
}