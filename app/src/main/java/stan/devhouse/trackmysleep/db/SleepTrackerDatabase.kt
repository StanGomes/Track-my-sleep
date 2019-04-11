package stan.devhouse.trackmysleep.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity

@Database(entities = [DailySleepQualityEntity::class], version = 1, exportSchema = false)
abstract class SleepTrackerDatabase : RoomDatabase() {
    abstract val sleepTrackerDao: SleepTrackerDao

    companion object {
        @Volatile
        private var INSTANCE: SleepTrackerDatabase? = null

        fun getInstance(context: Context): SleepTrackerDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SleepTrackerDatabase::class.java,
                        "sleep_quality_db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
                return instance
            }
        }
    }

}