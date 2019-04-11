package stan.devhouse.trackmysleep.sleepquality

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import stan.devhouse.trackmysleep.R
import stan.devhouse.trackmysleep.databinding.SleepQualityFragmentBinding
import stan.devhouse.trackmysleep.db.SleepTrackerDatabase

class SleepQualityFragment : Fragment() {


    private lateinit var sleepQualityViewModel: SleepQualityViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SleepQualityFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.sleep_quality_fragment, container, false)

        val application = requireNotNull(this.activity).application


        val args = SleepQualityFragmentArgs.fromBundle(arguments!!)
        val dataSource = SleepTrackerDatabase.getInstance(application).sleepTrackerDao
        val viewModelFactory = SleepQualityViewModelFactory(dataSource, args.sleepQualityKey)

        sleepQualityViewModel = ViewModelProviders.of(this, viewModelFactory).get(SleepQualityViewModel::class.java)

        binding.sleepQualityViewModel = sleepQualityViewModel

        sleepQualityViewModel.navigateToSleepTracker.observe(this, Observer {
            if (it == true){ //Observed state is true
                this.findNavController().navigate(SleepQualityFragmentDirections.actionSleepQualityFragmentToSleepTrackerFragment())
                sleepQualityViewModel.onNavigationDone()
            }
        })

        return binding.root
    }

}
