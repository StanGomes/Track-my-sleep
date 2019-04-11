package stan.devhouse.trackmysleep

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import stan.devhouse.trackmysleep.db.SleepTrackerDao
import stan.devhouse.trackmysleep.db.SleepTrackerDatabase
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SleepTrackerDatabaseTest {

    private lateinit var sleepTrackerDao: SleepTrackerDao
    private lateinit var db: SleepTrackerDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        db = Room.inMemoryDatabaseBuilder(context, SleepTrackerDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        sleepTrackerDao = db.sleepTrackerDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb(){
        db.close()
    }

    @Test
    @Throws(IOException::class)
    fun insertAndGetSleepQuality(){
        val quality = DailySleepQualityEntity()
        sleepTrackerDao.insert(quality)
        val latest = sleepTrackerDao.getLatestSleepQuality()
        assertEquals(latest?.qualityRating, -1)
    }

    @Test
    @Throws(IOException::class)
    fun updateSleepQuality(){
        val qualityEntity = DailySleepQualityEntity()
        sleepTrackerDao.insert(qualityEntity)
        sleepTrackerDao.update(qualityEntity)
        val latest = sleepTrackerDao.getLatestSleepQuality()
        assertEquals(latest?.qualityRating, -1)
    }

}