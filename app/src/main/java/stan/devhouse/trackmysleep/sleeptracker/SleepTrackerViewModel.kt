package stan.devhouse.trackmysleep.sleeptracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.*
import stan.devhouse.trackmysleep.db.SleepTrackerDao
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity

class SleepTrackerViewModel(private val db: SleepTrackerDao, application: Application) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var currentNight = MutableLiveData<DailySleepQualityEntity?>()

    val allNights = db.getAllSleepQuality()

    private val _navigateToSleepQuality = MutableLiveData<DailySleepQualityEntity>()
    val navigateToSleepQuality: LiveData<DailySleepQualityEntity> = _navigateToSleepQuality

    private val _showSnackBarEvent = MutableLiveData<Boolean>()
    val showSnackBarEvent: LiveData<Boolean> = _showSnackBarEvent

    private val _navigateToSleepDetail = MutableLiveData<Long>()
    val navigateToSleepDetail = _navigateToSleepDetail


    //Visible when currentNight is null
    val startButtonVisibility = Transformations.map(currentNight) {
        null == it
    }!!
    //Visible when currentNight is not null
    val stopButtonVisibility = Transformations.map(currentNight) {
        null != it
    }!!
    //Visible if all nights is not empty
    val clearButtonVisibility = Transformations.map(allNights) {
        it?.isNotEmpty()
    }!!

    init {
        initializeCurrentNight()
    }

    private fun initializeCurrentNight() {
        uiScope.launch {
            currentNight.value = getCurrentNightFromDb()
        }
    }

    private suspend fun getCurrentNightFromDb(): DailySleepQualityEntity? {
        return withContext(Dispatchers.IO) {
            var night = db.getLatestSleepQuality()
            if (night?.endTime != night?.startTime) {
                night = null
            }
            night
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onStartTracking() {
        uiScope.launch {
            val newNight = DailySleepQualityEntity()
            insert(newNight)
            currentNight.value = getCurrentNightFromDb()
        }
    }

    private suspend fun insert(night: DailySleepQualityEntity) {
        withContext(Dispatchers.IO) {
            db.insert(night)
        }
    }

    fun onStopTracking() {
        uiScope.launch {
            val oldNight = currentNight.value ?: return@launch
            oldNight.endTime = System.currentTimeMillis()
            update(oldNight)
            _navigateToSleepQuality.value = oldNight
        }
    }

    fun onNavigationDone() {
        _navigateToSleepQuality.value = null
    }

    fun onSnackBarShowed() {
        _showSnackBarEvent.value = false
    }

    private suspend fun update(night: DailySleepQualityEntity) {
        withContext(Dispatchers.IO) {
            db.update(night)
        }
    }

    fun onClear() {
        uiScope.launch {
            clear()
            currentNight.value = null
            _showSnackBarEvent.value = true
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            db.clear()
        }
    }

    fun onSleepNightClicked(sleepId: Long) {
        _navigateToSleepDetail.value = sleepId
    }

    fun onNavigatedToSleepDetail(){
        _navigateToSleepDetail.value = null
    }
}