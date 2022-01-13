package com.zdran.criminalintent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zdran.criminalintent.Crime

/**
 * 数据库类
 */
@Database(entities = [Crime::class], version = 1)
abstract class CrimeDatabase : RoomDatabase() {
}