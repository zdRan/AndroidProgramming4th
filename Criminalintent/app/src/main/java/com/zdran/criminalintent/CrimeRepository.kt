package com.zdran.criminalintent

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.zdran.criminalintent.database.CrimeDatabase
import java.util.*

private const val DATA_NAME = "crime-database"

class CrimeRepository private constructor(context: Context) {
    private val database: CrimeDatabase = Room.databaseBuilder(
        context.applicationContext,
        CrimeDatabase::class.java,
        DATA_NAME
    ).build()
    private val crimeDAO = database.crimeDAO()

    fun getCrimes(): LiveData<List<Crime>> = crimeDAO.getCrimes()
    fun getCrime(uuid: UUID): LiveData<Crime?> = crimeDAO.getCrime(uuid)
    fun addCrime(crime: Crime) = crimeDAO.addCrime(crime)

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?: throw IllegalStateException("数据库仓库未初始化.")
        }
    }
}