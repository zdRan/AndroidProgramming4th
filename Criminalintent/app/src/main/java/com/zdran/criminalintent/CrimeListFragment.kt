package com.zdran.criminalintent

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

private const val TAG = "CrimeListFragment"

class CrimeListFragment : Fragment() {
    private val crimeListViewModel: CrimeListViewModel by lazy {
        ViewModelProvider(this)[CrimeListViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: total crimes :${crimeListViewModel.crimes.size}")
    }

    //伴生对象
    companion object {
        fun newInstance(): CrimeListFragment {
            return CrimeListFragment()
        }
    }
}