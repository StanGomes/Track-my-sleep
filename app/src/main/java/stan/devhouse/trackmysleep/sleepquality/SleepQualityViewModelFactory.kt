package stan.devhouse.trackmysleep.sleepquality

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stan.devhouse.trackmysleep.db.SleepTrackerDao

class SleepQualityViewModelFactory(private val dataSource: SleepTrackerDao,
                                   private val sleepKey: Long) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepQualityViewModel::class.java)) {
            return SleepQualityViewModel(sleepKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}