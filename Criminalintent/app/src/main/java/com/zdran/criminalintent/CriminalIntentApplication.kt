package com.zdran.criminalintent

import android.app.Application
import java.util.*
import java.util.concurrent.Executors

class CriminalIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化数据库仓库对象
        CrimeRepository.initialize(this)
        Executors.newSingleThreadExecutor().execute {
            for (i in 1..10){
                val crime = Crime()
                crime.title = "title#${i}"
                crime.data = Date()
                crime.isSolved = i % 2 == 0
                CrimeRepository.get().addCrime(crime)

            }
        }

    }
}