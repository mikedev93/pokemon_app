package com.esteban.pokemonapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.Fragment
import com.esteban.pokemonapp.R
import com.esteban.pokemonapp.adapters.HomeViewPagerAdapter
import com.esteban.pokemonapp.ui.home.captured.CapturedFragment
import com.esteban.pokemonapp.ui.home.community.CommunityFragment
import com.esteban.pokemonapp.ui.home.explore.ExploreFragment
import com.esteban.pokemonapp.ui.home.myTeam.MyTeamFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_home.view.*

@AndroidEntryPoint
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val fragmentList = arrayListOf<Fragment>(
            ExploreFragment(),
            CommunityFragment(),
            MyTeamFragment(),
            CapturedFragment()
        )

        val adapter = HomeViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )

        view.viewPager.adapter = adapter
        TabLayoutMediator(view.tabLayout, view.viewPager) {tab, position ->
            tab.text = resources.getStringArray(R.array.menu)[position]
        }.attach()
        for (i in 0 until view.tabLayout.tabCount) {
            val shape = LayoutInflater.from(view.context).inflate(R.layout.layout_home_tab, null) as LinearLayout
            val textView = shape.findViewById<TextView>(R.id.text)
            if (i == 0) {
                val background = shape.findViewById<LinearLayout>(R.id.linear_background)
                background?.setBackgroundResource(R.drawable.background_tab_selected)
                textView.setTextColor(getColor(view.context, R.color.white))
            }
            textView.text = view.tabLayout.getTabAt(i)?.text
            view.tabLayout.getTabAt(i)?.customView = shape
        }

        view.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                selectTab(tab.parent, tab.position)
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {
                unselectTab(tab.parent, tab.position)
            }

            override fun onTabReselected(tab: TabLayout.Tab) {}
        })

        return view
    }

    fun selectTab(tabLayout: TabLayout?, position: Int) {
        val tabShape = tabLayout?.getTabAt(position)?.customView
        val text = tabShape?.findViewById<TextView>(R.id.text)
        text?.setTextColor(getColor(tabLayout.context, R.color.white))
        val background = tabShape?.findViewById<LinearLayout>(R.id.linear_background)
        background?.setBackgroundResource(R.drawable.background_tab_selected)
    }

    fun unselectTab(tabLayout: TabLayout?, position: Int) {
        val tabShape = tabLayout?.getTabAt(position)?.customView
        val text = tabShape?.findViewById<TextView>(R.id.text)
        text?.setTextColor(getColor(tabLayout.context, R.color.black))
        val background = tabShape?.findViewById<LinearLayout>(R.id.linear_background)
        background?.setBackgroundResource(R.drawable.background_tabs)
    }
}