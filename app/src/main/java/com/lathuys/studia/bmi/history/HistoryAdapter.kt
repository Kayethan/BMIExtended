package com.lathuys.studia.bmi.history

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.lathuys.studia.R
import com.lathuys.studia.bmi.BmiHelper
import java.util.*

class HistoryAdapter(private val entriesDataset: ArrayList<HistoryEntry>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    companion object {
        lateinit var context: Context
    }

    class HistoryViewHolder(entryView: View) : RecyclerView.ViewHolder(entryView) {
        val heightTextView: TextView = entryView.findViewById(R.id.heightEntryTV)
        val massTextView: TextView = entryView.findViewById(R.id.massEntryTV)
        val dateTextView: TextView = entryView.findViewById(R.id.dateEntryTV)
        val bmiTextView: TextView = entryView.findViewById(R.id.bmiEntryTV)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryViewHolder {
        val entryView =
            LayoutInflater.from(parent.context).inflate(R.layout.history_entry, parent, false)
        return HistoryViewHolder(entryView)
    }

    override fun onBindViewHolder(entryHolder: HistoryViewHolder, position: Int) {
        val entry: HistoryEntry = entriesDataset[position]
        entryHolder.heightTextView.text = context.getString(R.string.height) + ": " + entry.height
        entryHolder.massTextView.text = context.getString(R.string.mass) + ": " + entry.mass
        entryHolder.dateTextView.text = entry.date

        val bmiVal = entry.bmi.toFloat()
        entryHolder.bmiTextView.setTextColor(BmiHelper.getBmiColor(context, bmiVal))
        entryHolder.bmiTextView.text = entry.bmi
    }

    override fun getItemCount(): Int {
        return entriesDataset.size
    }
}