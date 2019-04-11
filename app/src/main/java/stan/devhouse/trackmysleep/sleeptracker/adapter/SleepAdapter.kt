package stan.devhouse.trackmysleep.sleeptracker.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity

class SleepAdapter(private val sleepList: MutableList<DailySleepQualityEntity>?) : RecyclerView.Adapter<SleepViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SleepViewHolder(inflater, parent)
    }

    override fun getItemCount(): Int = sleepList?.size!!

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
    }
}