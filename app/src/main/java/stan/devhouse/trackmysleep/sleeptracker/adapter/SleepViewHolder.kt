package stan.devhouse.trackmysleep.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import stan.devhouse.trackmysleep.R

class SleepViewHolder(inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(
    inflater.inflate(R.layout.rv_layout, parent, false)) {
    private var sleepText: TextView? = null

    init {
        sleepText = itemView.findViewById(R.id.rv_text)
    }
}