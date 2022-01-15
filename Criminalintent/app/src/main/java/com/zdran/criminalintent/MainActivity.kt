package com.zdran.criminalintent

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.util.*
private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity(), CrimeListFragment.Callbacks {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (currentFragment == null) {
            val crimeFragment = CrimeListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, crimeFragment)
                .commit()
        }
    }

    /**
     * 回调方法，用于处理 CrimeListFragment 的点击事件
     */
    override fun onCrimeSelected(crimeId: UUID) {
        Log.d(TAG, "onCrimeSelected: ${crimeId.toString()}")
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, CrimeFragment())
            .addToBackStack(null)
            .commit()
    }
}