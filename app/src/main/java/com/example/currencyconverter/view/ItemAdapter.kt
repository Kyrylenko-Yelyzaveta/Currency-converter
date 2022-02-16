package com.example.currencyconverter.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.model.entity.Item
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ItemHolderBinding
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries


class ItemAdapter : RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    private val itemList = ArrayList<Item>()

    class ItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val bind = ItemHolderBinding.bind(item)
        fun bind(item: Item) = with(bind)
        {
            txtTitleName.text = item.name
            txtLastValue.text = item.values[item.values.size - 1].toString()
            btnMonthCharts.setOnClickListener()
            {
                MainActivity.chartName = "month"
                val currencyChart = LineGraphSeries<DataPoint>()
                for (i in 0 until MONTH_DAYS)
                    currencyChart.appendData(DataPoint(i.toDouble(), item.values[i]),
                        true,
                        MONTH_DAYS)
                charts.viewport?.setMaxX(MONTH_DAYS.toDouble())
                charts.addSeries(currencyChart)
                charts.viewport?.isXAxisBoundsManual = true
                charts.viewport?.isScalable = true
                charts.viewport?.isScrollable = true

            }
            btnWeekCharts.setOnClickListener()
            {
                MainActivity.chartName = "week"
                val currencyChart = LineGraphSeries<DataPoint>()
                for (i in 0 until WEEK_DAYS)
                    currencyChart.appendData(DataPoint(i.toDouble(), item.values[i]),
                        true,
                        WEEK_DAYS)
                charts.viewport?.setMaxX(WEEK_DAYS.toDouble())
                charts.addSeries(currencyChart)
                charts.viewport?.isXAxisBoundsManual = true
                charts.viewport?.isScalable = true
                charts.viewport?.isScrollable = true
            }

            cardView.setOnClickListener()
            {
                when {
                    charts.visibility == View.VISIBLE -> {
                        charts.visibility = View.GONE
                        layoutButtonsCharts.visibility = View.GONE
                        MainActivity.cryptoName = ""
                    }
                    item.values.size < 2 -> {
                        charts.visibility = View.GONE
                        layoutButtonsCharts.visibility = View.GONE
                    }
                    else -> {
                        charts.visibility = View.VISIBLE
                        layoutButtonsCharts.visibility = View.VISIBLE
                        MainActivity.cryptoName = item.name
                        when (MainActivity.chartName){
                            "week"-> btnWeekCharts.performClick()
                            "month"-> btnMonthCharts.performClick()
                            else -> {}
                        }
                    }
                }
            }


            if (MainActivity.cryptoName == item.name)
                cardView.performClick()








        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_holder, parent, false)
        return ItemHolder(view)
    }


    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun showItems(items: List<Item>) {
        itemList.clear()
        itemList.addAll(items)
    }

    companion object {
        const val WEEK_DAYS = 7
        const val MONTH_DAYS = 30
    }


}