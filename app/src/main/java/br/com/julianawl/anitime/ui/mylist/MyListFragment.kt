package br.com.julianawl.anitime.ui.mylist

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import br.com.julianawl.anitime.MyApplication
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.ui.adapter.ViewPagerAdapter
import br.com.julianawl.anitime.ui.discover.DiscoverViewModel
import br.com.julianawl.anitime.ui.discover.DiscoverViewModelFactory
import br.com.julianawl.anitime.ui.mylist.tabs.CompleteFragment
import br.com.julianawl.anitime.ui.mylist.tabs.PlanFragment
import kotlinx.android.synthetic.main.fragment_mylist.*


class MyListFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_mylist,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
    }
    private fun setupTabs(){
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(CompleteFragment(), "Complete")
        adapter.addFragment(PlanFragment(), "Plan to watch")

        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)

    }
}