package stan.devhouse.trackmysleep.sleepdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import stan.devhouse.trackmysleep.R
import stan.devhouse.trackmysleep.databinding.SleepDetailFragmentBinding
import stan.devhouse.trackmysleep.db.SleepTrackerDatabase

class SleepDetailFragment : Fragment() {

    private lateinit var viewModel: SleepDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: SleepDetailFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.sleep_detail_fragment, container, false)

        val application = requireNotNull(this.activity).application
        val args = SleepDetailFragmentArgs.fromBundle(arguments!!)

        val dataSource = SleepTrackerDatabase.getInstance(application).sleepTrackerDao
        val viewModelFactory = SleepDetailViewModelFactory(args.sleepKey, dataSource)

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SleepDetailViewModel::class.java)

        binding.sleepDetailViewModel = viewModel
        binding.lifecycleOwner = this

        viewModel.navigateToSleepTracker.observe(this, Observer {
            if (it == true) {
                this.findNavController()
                    .navigate(SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment())
                viewModel.navigationCompleted()
            }
        })

        return binding.root
    }


}
