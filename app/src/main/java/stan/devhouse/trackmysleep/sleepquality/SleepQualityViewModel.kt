package stan.devhouse.trackmysleep.sleepquality

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import stan.devhouse.trackmysleep.db.SleepTrackerDao

class SleepQualityViewModel(private val sleepKey: Long = 0L,
                            val db: SleepTrackerDao) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val _navigateToSleepTracker = MutableLiveData<Boolean?>()
    val navigateToSleepTracker : LiveData<Boolean?>
    get () = _navigateToSleepTracker

    fun onNavigationDone(){
        _navigateToSleepTracker.value = null
    }

    fun onQualityBtnPressed(quality: Int){
        uiScope.launch {
            withContext(Dispatchers.IO){
                val tonight = db.get(sleepKey) ?: return@withContext
                tonight.qualityRating = quality
                db.update(tonight)
            }
            _navigateToSleepTracker.value = true
        }
    }

}
