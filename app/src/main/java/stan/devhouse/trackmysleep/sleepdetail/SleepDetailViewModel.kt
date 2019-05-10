package stan.devhouse.trackmysleep.sleepdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import stan.devhouse.trackmysleep.db.SleepTrackerDao
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity

/**
 * ViewModel for SleepDetailFragment
 */

class SleepDetailViewModel(private val sleepKey: Long = 0L,
                           dataSource: SleepTrackerDao) : ViewModel() {

    val database = dataSource
    private val viewModelJob = Job()
    private val night = MediatorLiveData<DailySleepQualityEntity>()

    fun getNight() = night

    init {
        night.addSource(database.getSleepWithId(sleepKey), night::setValue)
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()

    val navigateToSleepTracker : LiveData<Boolean?> = _navigateToSleepTracker

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun navigationCompleted(){
        _navigateToSleepTracker.value = null
    }

    fun onClose(){
        _navigateToSleepTracker.value = true
    }

}
