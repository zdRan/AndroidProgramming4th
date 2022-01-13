package com.zdran.criminalintent

import android.app.Application

class CriminalIntentApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        //初始化数据库仓库对象
        CrimeRepository.initialize(this)
    }
}