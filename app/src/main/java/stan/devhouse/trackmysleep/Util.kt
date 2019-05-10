package stan.devhouse.trackmysleep

import android.annotation.SuppressLint
import android.content.res.Resources
import android.os.Build
import android.text.Html
import android.text.Spanned
import androidx.core.text.HtmlCompat
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * These functions create a formatted string that can be set in a TextView
 */

private val ONE_MINUTE_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES)
private val ONE_HOUR_MILLIS = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

/**
 * Convert a duration to a formatted string
 * eg : 9 hours on Monday
 */
fun convertDurationToFormattedString(startTimeMilli: Long, endTimeMilli: Long, res: Resources): String {
    val durationMilli = endTimeMilli - startTimeMilli
    val weekdayString = SimpleDateFormat("EEEE", Locale.getDefault()).format(startTimeMilli)
    return when {
        durationMilli < ONE_MINUTE_MILLIS -> {
            val seconds = TimeUnit.SECONDS.convert(durationMilli, TimeUnit.MILLISECONDS)
            res.getString(R.string.seconds_length, seconds, weekdayString)
        }
        durationMilli < ONE_HOUR_MILLIS -> {
            val minutes = TimeUnit.MINUTES.convert(durationMilli, TimeUnit.MINUTES)
            res.getString(R.string.minutes_length, minutes, weekdayString)
        }
        else -> {
            val hours = TimeUnit.HOURS.convert(durationMilli, TimeUnit.HOURS)
            res.getString(R.string.hours_length, hours, weekdayString)
        }
    }
}

fun convertNumericQualityToString(quality: Int): String {
    return when (quality) {
        -1 -> "--"
        0 -> "Very Bad"
        1 -> "Poor"
        2 -> "Meh"
        3 -> "Decent"
        4 -> "Pretty Good"
        5 -> "Excellent"
        else -> "Null"
    }
}

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm").format(systemTime).toString()
}
//
//fun formatNightsString(nights: List<DailySleepQualityEntity>, resources: Resources): Spanned {
//    val stringBuilder = StringBuilder()
//    stringBuilder.apply {
//        append(resources.getString(R.string.title))
//        nights.forEach{
//            append("<br>")
//            append(resources.getString(R.string.start_time))
//            append("\t${convertLongToDateString(it.startTime)}<br>")
//            if (it.endTime != it.startTime) {
//                append(resources.getString(R.string.end_time))
//                append("\t${convertLongToDateString(it.endTime)}<br>")
//                append(resources.getString(R.string.quality))
//                append("\t${convertNumericQualityToString(it.qualityRating)}<br>")
//                append(resources.getString(R.string.hours_slept))
//                //Hours
//                append("\t ${it.endTime.minus(it.startTime) / 1000 / 60 / 60}:")
//                //Minutes
//                append("${it.endTime.minus(it.startTime) /1000/60}:")
//                //Seconds
//                append("${it.endTime.minus(it.startTime) / 1000}")
//                append("<br>") }
//        }
//    }
//    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
//        Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_LEGACY)
//    } else {
//        HtmlCompat.fromHtml(stringBuilder.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
//    }
//}