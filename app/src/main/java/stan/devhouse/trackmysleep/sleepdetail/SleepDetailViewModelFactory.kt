package stan.devhouse.trackmysleep.sleepdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stan.devhouse.trackmysleep.db.SleepTrackerDao

/**
 * This is pretty much boiler plate code for a ViewModel Factory.
 *
 * Provides the key for the night and the SleepDatabaseDao to the ViewModel.
 */
class SleepDetailViewModelFactory(
    private val sleepKey: Long,
    private val dataSource: SleepTrackerDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SleepDetailViewModel::class.java)) {
            return SleepDetailViewModel(sleepKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}