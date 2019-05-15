package stan.devhouse.trackmysleep.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import stan.devhouse.trackmysleep.databinding.ItemSleepListBinding
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity

class SleepListAdapter(private val clickListener: SleepListListener) : ListAdapter<DailySleepQualityEntity, ViewHolder>(SleepListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(clickListener, item)
    }
}

class ViewHolder private constructor(private val binding: ItemSleepListBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSleepListBinding.inflate(layoutInflater, parent, false)

            return ViewHolder(binding)
        }
    }

    fun bind(clickListener: SleepListListener,item: DailySleepQualityEntity) {
        binding.sleep = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

class SleepListDiffUtilCallback : DiffUtil.ItemCallback<DailySleepQualityEntity>() {

    override fun areItemsTheSame(oldItem: DailySleepQualityEntity, newItem: DailySleepQualityEntity): Boolean {
        return oldItem.sleepId == newItem.sleepId
    }

    override fun areContentsTheSame(oldItem: DailySleepQualityEntity, newItem: DailySleepQualityEntity): Boolean {
        return oldItem == newItem
    }
}

class SleepListListener(val clickListener: (sleepId: Long) -> Unit){
    fun onClick(item: DailySleepQualityEntity) = clickListener(item.sleepId)
}
