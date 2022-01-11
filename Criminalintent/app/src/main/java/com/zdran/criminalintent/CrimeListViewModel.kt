package com.zdran.criminalintent

import androidx.lifecycle.ViewModel

//crime 列表
class CrimeListViewModel : ViewModel() {
    val crimes = mutableListOf<Crime>()

    init {
        for (i in 0 until 100) {
            val crime = Crime()
            crime.title = "Crime #$i"
            crime.isSolved = i % 2 == 0
            crimes += crime
            crime.requiresPolice = i % 3 == 0
        }
    }
}