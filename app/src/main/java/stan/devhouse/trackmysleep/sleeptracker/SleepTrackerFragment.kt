package stan.devhouse.trackmysleep.sleeptracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.sleep_tracker_test_fragment.*
import stan.devhouse.trackmysleep.R
import stan.devhouse.trackmysleep.databinding.SleepTrackerTestFragmentBinding
import stan.devhouse.trackmysleep.db.SleepTrackerDatabase
import stan.devhouse.trackmysleep.db.entity.DailySleepQualityEntity
import stan.devhouse.trackmysleep.sleeptracker.adapter.SleepAdapter

class SleepTrackerFragment : Fragment() {


    private lateinit var viewModel: SleepTrackerViewModel
    private var sleepList: MutableList<DailySleepQualityEntity>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SleepTrackerTestFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.sleep_tracker_test_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = SleepTrackerDatabase.getInstance(application).sleepTrackerDao
        val viewModelFactory = SleepTrackerViewModelFactory(dataSource, application)
        val sleepTrackerViewModel = ViewModelProviders.of(this, viewModelFactory).get(SleepTrackerViewModel::class.java)

        binding.lifecycleOwner = this

        binding.sleepTrackerViewModel = sleepTrackerViewModel


        sleepTrackerViewModel.navigateToSleepQuality.observe(this, Observer { night ->
            night?.let {
                sleepList?.add(night)
                this.findNavController().navigate(SleepTrackerFragmentDirections.actionSleepTrackerFragmentToSleepQualityFragment(night.sleepId))
                initRecyclerView(sleepList)
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

        return binding.root
    }

    private fun initRecyclerView(night: MutableList<DailySleepQualityEntity>?){
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = SleepAdapter(night)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SleepTrackerViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
