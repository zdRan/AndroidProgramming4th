package com.zdran.criminalintent

import androidx.lifecycle.ViewModel

//crime 列表
class CrimeListViewModel : ViewModel() {
    private val crimeRepository = CrimeRepository.get()
    val crimeListLiveData = crimeRepository.getCrimes()
}