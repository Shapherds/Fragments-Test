package com.example.pecode_test

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.pecode_test.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var uiBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(uiBinding.root)
        pagerAdapter = ViewPagerAdapter(supportFragmentManager, lifecycle, uiBinding.ViewPager)
        uiBinding.ViewPager.adapter = pagerAdapter
        pagerAdapter.createFragment(0)
    }

    override fun onBackPressed() {
        if (uiBinding.ViewPager.currentItem == 0) {
            super.onBackPressed()
        } else {
            pagerAdapter.removeFragment(uiBinding.ViewPager.currentItem)
            uiBinding.ViewPager.currentItem = --uiBinding.ViewPager.currentItem
        }
    }

    private fun showLog(text: String) {
        Log.e("Logs", text)
    }
}