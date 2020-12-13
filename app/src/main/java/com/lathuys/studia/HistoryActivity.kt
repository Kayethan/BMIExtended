package com.lathuys.studia

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.lathuys.studia.bmi.BmiHelper
import com.lathuys.studia.bmi.history.EntryDivider
import com.lathuys.studia.bmi.history.HistoryAdapter
import com.lathuys.studia.bmi.history.HistoryEntry
import com.lathuys.studia.databinding.ActivityHistoryBinding
import com.lathuys.studia.room.HistoryDatabase
import kotlinx.android.synthetic.main.activity_history.*
import java.util.ArrayList

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionBar = supportActionBar
        actionBar!!.title = getString(R.string.historyTitle)

        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewManager = LinearLayoutManager(this)

        val entries = ArrayList<HistoryEntry>()
        for (entry in HistoryDatabase.getDatabase(this).historyEntryDao().getAll()) {
            entries.add(HistoryEntry(entry.height, entry.mass, entry.bmi, entry.date))
        }

        HistoryAdapter.context = this@HistoryActivity
        viewAdapter = HistoryAdapter(entries)

        recyclerView = historyRV.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        recyclerView.addItemDecoration(
            EntryDivider(this)
        )
    }
}