package com.example.pecode_test

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2

class ViewPagerAdapter(
    manager: FragmentManager,
    lifecycle: Lifecycle,
    private val viewPager2: ViewPager2
) :
    FragmentStateAdapter(manager, lifecycle) {

    private val hashMap = HashMap<Int, Fragment>()

    override fun getItemCount() = hashMap.size

    override fun createFragment(position: Int): Fragment {
        if (!hashMap.keys.contains(position)) {
            hashMap[position] = Counter(position+1, this, viewPager2)
        }
        return hashMap[position] ?: throw NullPointerException()
    }

    fun removeFragment(position: Int) {
        if (position != 0) {
            hashMap.remove(position-1)
            notifyDataSetChanged()
        }
    }
}