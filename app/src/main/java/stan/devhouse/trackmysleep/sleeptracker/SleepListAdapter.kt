package stan.devhouse.trackmysleep.sleeptracker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import stan.devhouse.trackmysleep.R
import stan.devhouse.trackmysleep.convertDurationToFormattedString
import stan.devhouse.trackmysleep.convertNumericQualityToString
import stan.devhouse.trackmysleep.databinding.ItemSleepListBinding
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity

class SleepListAdapter : ListAdapter<DailySleepQualityEntity, ViewHolder>(SleepListDiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }
}

class ViewHolder private constructor(val binding: ItemSleepListBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemSleepListBinding.inflate(layoutInflater, parent, false)

            return ViewHolder(binding)
        }
    }

    fun bind(item: DailySleepQualityEntity) {
        binding.sleep = item
        binding.executePendingBindings()
//        val res = itemView.context.resources
//        binding.durationTextView.text = convertDurationToFormattedString(item.startTime, item.endTime, res)
//        binding.qualityTextView.text = convertNumericQualityToString(item.qualityRating)
//        binding.qualityImage.setImageResource(
//            when (item.qualityRating) {
//                0 -> R.drawable.ic_sleep_0
//                1 -> R.drawable.ic_sleep_1
//                2 -> R.drawable.ic_sleep_2
//                3 -> R.drawable.ic_sleep_3
//                4 -> R.drawable.ic_sleep_4
//                5 -> R.drawable.ic_sleep_5
//                else -> R.drawable.ic_sleep_active
//            }
//        )
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
