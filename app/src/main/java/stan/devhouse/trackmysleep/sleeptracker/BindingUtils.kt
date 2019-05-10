package stan.devhouse.trackmysleep.sleeptracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import stan.devhouse.trackmysleep.R
import stan.devhouse.trackmysleep.convertDurationToFormattedString
import stan.devhouse.trackmysleep.convertNumericQualityToString
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity

@BindingAdapter("sleepDurationFormatted")
fun TextView.setSleepDurationFormatted(item: DailySleepQualityEntity?) {
    item?.let {
        text = convertDurationToFormattedString(item.startTime, item.endTime, context.resources)
    }
}

@BindingAdapter("sleepQualityFormatted")
fun TextView.setSleepQualityFormatted(item: DailySleepQualityEntity?) {
    item?.let {
        text = convertNumericQualityToString(item.qualityRating)
    }
}

@BindingAdapter("qualityImage")
fun ImageView.setQualityImage(item: DailySleepQualityEntity?){
    setImageResource(when(item?.qualityRating){
        0 -> R.drawable.ic_sleep_0
        1 -> R.drawable.ic_sleep_1
        2 -> R.drawable.ic_sleep_2
        3 -> R.drawable.ic_sleep_3
        4 -> R.drawable.ic_sleep_4
        5 -> R.drawable.ic_sleep_5
        else -> R.drawable.ic_sleep_active
    })
}