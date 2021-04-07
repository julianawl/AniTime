package br.com.julianawl.anitime.ui.mylist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import br.com.julianawl.anitime.R
import br.com.julianawl.anitime.ui.COMPLETE
import br.com.julianawl.anitime.ui.PLAN_TO_WATCH
import br.com.julianawl.anitime.ui.adapter.ViewPagerAdapter
import br.com.julianawl.anitime.ui.mylist.tabs.CompleteFragment
import br.com.julianawl.anitime.ui.mylist.tabs.PlanFragment
import com.google.android.material.appbar.MaterialToolbar
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

        configuraAppBar(view)
        setupTabs()
    }

    //configura a appbar do mylist
    private fun configuraAppBar(view: View) {
        val toolbar = view.findViewById<MaterialToolbar>(R.id.topAppBarList)
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_mylist))
        val navHostFragment = NavHostFragment.findNavController(this)

        NavigationUI.setupWithNavController(toolbar, navHostFragment, appBarConfiguration)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

//        toolbar.setNavigationOnClickListener { view ->
//            view.findNavController().navigateUp()
//        }
    }

    //configura o tablayout
    private fun setupTabs() {
        val adapter = ViewPagerAdapter(requireActivity().supportFragmentManager)
        adapter.addFragment(CompleteFragment(), COMPLETE)
        adapter.addFragment(PlanFragment(), PLAN_TO_WATCH)

        view_pager.adapter = adapter
        tab_layout.setupWithViewPager(view_pager)
    }
}