package com.hikanala.currencyconvertor.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hikanala.currencyconvertor.R
import com.hikanala.currencyconvertor.data.Currency

class CountryAdapter(private val dataList: List<Currency>, private val onItemClick: (Currency) -> Unit) :
    RecyclerView.Adapter<CountryAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCurrency: TextView = itemView.findViewById(R.id.tv_currency)
        val ivFlag: ImageView = itemView.findViewById(R.id.iv_flag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = dataList[position]
        holder.tvCurrency.text = item.currency
        holder.ivFlag.setBackgroundResource(item.flag)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}
