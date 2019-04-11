package stan.devhouse.trackmysleep.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity

@Dao
interface SleepTrackerDao {

    @Insert
    fun insert(sleepQuality: DailySleepQualityEntity)

    @Update
    fun update(sleepQuality: DailySleepQualityEntity)

    @Query("select * from daily_sleep_quality_table where sleepId = :key")
    fun get(key: Long): DailySleepQualityEntity?

    @Query("delete from daily_sleep_quality_table")
    fun clear()

    @Query("select * from daily_sleep_quality_table order by sleepId desc")
    fun getAllSleepQuality(): LiveData<List<DailySleepQualityEntity>>

    @Query("select * from daily_sleep_quality_table order by sleepId desc limit 1")
    fun getLatestSleepQuality(): DailySleepQualityEntity?
}