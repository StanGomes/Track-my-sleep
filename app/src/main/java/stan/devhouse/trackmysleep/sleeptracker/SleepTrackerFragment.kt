package stan.devhouse.trackmysleep.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import stan.devhouse.trackmysleep.R
import stan.devhouse.trackmysleep.databinding.SleepTrackerFragmentBinding
import stan.devhouse.trackmysleep.db.SleepTrackerDatabase
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity


class SleepTrackerFragment : Fragment() {

    private var sleepList: MutableList<DailySleepQualityEntity>? = null

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val binding: SleepTrackerFragmentBinding =
                DataBindingUtil.inflate(inflater, R.layout.sleep_tracker_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = SleepTrackerDatabase.getInstance(application).sleepTrackerDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel = ViewModelProviders.of(this, viewModelFactory).get(SleepTrackerViewModel::class.java)
        val layoutManager = GridLayoutManager(activity, 3)

        binding.sleepTrackerViewModel = sleepTrackerViewModel
        binding.lifecycleOwner = this
        binding.recyclerView.layoutManager = layoutManager

        val adapter = SleepListAdapter(SleepListListener { sleepId ->
            sleepTrackerViewModel.onSleepNightClicked(sleepId)
        })
        binding.recyclerView.adapter = adapter

        sleepTrackerViewModel.navigateToSleepQuality.observe(this, Observer { night ->
            night?.let {
                sleepList?.add(night)
                this.findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(night.sleepId))
                sleepTrackerViewModel.onNavigationDone()
            }
        })

        sleepTrackerViewModel.showSnackBarEvent.observe(this, Observer {
            if (it == true) {
                Snackbar.make(
                        activity!!.findViewById(android.R.id.content), getString(R.string.cleared_message),
                        Snackbar.LENGTH_SHORT
                ).show()
                sleepTrackerViewModel.onSnackBarShowed()
            }
        })

        sleepTrackerViewModel.allNights.observe(this, Observer { list ->
            list?.let {
                adapter.submitList(it)
            }
        })

        sleepTrackerViewModel.navigateToSleepDetail.observe(this, Observer { sleep ->
            sleep?.let {
                this.findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepDetailFragment(sleep))
                sleepTrackerViewModel.onNavigatedToSleepDetail()
            }
        })

        return binding.root
    }


}
