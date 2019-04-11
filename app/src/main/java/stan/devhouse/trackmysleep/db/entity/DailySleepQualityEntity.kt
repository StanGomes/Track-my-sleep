package stan.devhouse.trackmysleep.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_sleep_quality_table")
data class DailySleepQualityEntity(
    @PrimaryKey(autoGenerate = true)
    var sleepId: Long = 0L,
    @ColumnInfo(name = "start_time_milli")
    var startTime: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "end_time_milli")
    var endTime: Long = startTime,
    @ColumnInfo(name = "quality_rating")
    var qualityRating: Int = -1
)