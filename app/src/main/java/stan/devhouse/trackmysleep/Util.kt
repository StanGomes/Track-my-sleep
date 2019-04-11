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

fun convertNumericQualityToString(quality: Int, resource: Resources): String {
    var qualityString = "OK"
    when (quality) {
        -1 -> qualityString = "--"
        0 -> qualityString = "Very Bad"
        1 -> qualityString = "Poor"
        2 -> qualityString = "Meh"
        4 -> qualityString = "Pretty Good"
        5 -> qualityString = "Excellent"
        else -> qualityString = "Null"
    }

    return qualityString
}

@SuppressLint("SimpleDateFormat")
fun convertLongToDateString(systemTime: Long): String {
    return SimpleDateFormat("EEEE MMM-dd-yyyy' Time: 'HH:mm").format(systemTime).toString()
}

fun formatNightsString(nights: List<DailySleepQualityEntity>, resources: Resources): Spanned {
    val stringBuilder = StringBuilder()
    stringBuilder.apply {
        append(resources.getString(R.string.title))
        nights.forEach{
            append("<br>")
            append(resources.getString(R.string.start_time))
            append("\t${convertLongToDateString(it.startTime)}<br>")
            if (it.endTime != it.startTime) {
                append(resources.getString(R.string.end_time))
                append("\t${convertLongToDateString(it.endTime)}<br>")
                append(resources.getString(R.string.quality))
                append("\t${convertNumericQualityToString(it.qualityRating, resources)}<br>")
                append(resources.getString(R.string.hours_slept))
                //Hours
                append("\t ${it.endTime.minus(it.startTime) / 1000 / 60 / 60}:")
                //Minutes
                append("${it.endTime.minus(it.startTime) /1000/60}:")
                //Seconds
                append("${it.endTime.minus(it.startTime) / 1000}")
                append("<br>") }
        }
    }
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
        Html.fromHtml(stringBuilder.toString(), Html.FROM_HTML_MODE_LEGACY)
    } else {
        HtmlCompat.fromHtml(stringBuilder.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY)
    }
}